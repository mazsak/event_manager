package com.example.event_manager.service;

import com.example.event_manager.model.BillingRaportSchema;
import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface BillingReportService {

  String generateXmlForBillingReportSchema(BillingRaportSchema brs);

  byte[] convertBillingReportSchemaToByteStream(BillingRaportSchema brs)
      throws IOException, FOPException, TransformerException;
}
