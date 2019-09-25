package com.example.event_manager.service;

import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.BillingRaportSchema;
import com.thoughtworks.xstream.XStream;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

@Service
public class BillingReportServiceImpl implements BillingReportService {

  @Value("classpath:static/newStyle.xsl")
  Resource styleXslResource;

  public String generateXmlForBillingReportSchema(final BillingRaportSchema brs) {
    final XStream xStream = new XStream();
    xStream.alias("BillingReportSchema", BillingRaportSchema.class);
    xStream.alias("billing", BillingForm.class);
    xStream.addImplicitCollection(BillingRaportSchema.class, "billings");
    return xStream.toXML(brs);
  }

  public byte[] convertBillingReportSchemaToByteStream(final BillingRaportSchema brs)
      throws IOException, FOPException, TransformerException {
    final String xml = generateXmlForBillingReportSchema(brs);
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
