package com.example.event_manager.utils.raport;

import com.example.event_manager.form.BillingForm;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

public class BillingRaport {


  private final String pathToBillingInStatic = "src/main/resources/static/billing/";
  private final BillingRaportSchema billingRaportSchema;
  private String billingsXml;

  public BillingRaport(final BillingRaportSchema billingRaportSchema) {
    this.billingRaportSchema = billingRaportSchema;
  }

  private void generateXmlForBillingRaportSchema() {
    final XStream xStream = new XStream();
    xStream.alias("BillingRaportSchema", BillingRaportSchema.class);
    xStream.alias("billing", BillingForm.class);
    xStream.addImplicitCollection(BillingRaportSchema.class, "billings");
    billingsXml = xStream.toXML(billingRaportSchema);

  }

  public String convertXmlToPdfAndSaveOnDisc()
      throws IOException, FOPException, TransformerException {
    generateXmlForBillingRaportSchema();
    final File xsltFile = new File(
        pathToBillingInStatic +"newStyle.xsl");
    final StreamSource xmlSource = new StreamSource(new StringReader(billingsXml));
    final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    final FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
    final OutputStream out;
    out = new java.io.FileOutputStream(pathToBillingInStatic +"billings.pdf");
    try {
      final Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
      final TransformerFactory factory = TransformerFactory.newInstance();
      final Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
      final Result res = new SAXResult(fop.getDefaultHandler());
      transformer.transform(xmlSource, res);
      return pathToBillingInStatic +"billings.pdf";
    } finally {
      out.close();
    }
  }
}


