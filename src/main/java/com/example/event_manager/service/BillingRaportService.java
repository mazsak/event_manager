package com.example.event_manager.service;

import com.example.event_manager.model.BillingRaportSchema;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.apache.fop.apps.FOPException;

public interface BillingRaportService {

  String generateXmlForBillingRaportSchema(BillingRaportSchema brs);

  byte[] convertBillingRaportSchemaToByteStream(BillingRaportSchema brs)
      throws IOException, FOPException, TransformerException;

}
