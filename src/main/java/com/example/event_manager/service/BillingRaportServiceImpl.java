package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.BillingRaportSchema;
import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class BillingRaportServiceImpl implements BillingRaportService {


  @Value("classpath:static/newStyle.xsl")
  Resource styleXslResource;

  public String generateXmlForBillingRaportSchema(BillingRaportSchema brs) {
    final XStream xStream = new XStream();
    xStream.alias("BillingRaportSchema", BillingRaportSchema.class);
    xStream.alias("billing", BillingForm.class);
    xStream.addImplicitCollection(BillingRaportSchema.class, "billings");
    return xStream.toXML(brs);
  }

  public byte[] convertBillingRaportSchemaToByteStream(BillingRaportSchema brs)
      throws IOException, FOPException, TransformerException {
    String xml = generateXmlForBillingRaportSchema(brs);
    final File xsltFile = styleXslResource.getFile();
    final StreamSource xmlSource = new StreamSource(new StringReader(xml));
    final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    final FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try {
      final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, byteArrayOutputStream);
      final TransformerFactory factory = TransformerFactory.newInstance();
      final Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
      final Result res = new SAXResult(fop.getDefaultHandler());
      transformer.transform(xmlSource, res);
      return byteArrayOutputStream.toByteArray();
    } finally {
      byteArrayOutputStream.close();
    }
  }
}

