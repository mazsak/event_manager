package com.example.event_manager.service;

import com.example.event_manager.form.BillingFilter;
import com.example.event_manager.form.BillingForm;
import com.example.event_manager.model.BillingType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class BillingReportServiceImpl implements BillingReportService {

  @Value("classpath:static/newStyle.xsl")
  Resource styleXslResource;
  private static final int TITLE_IDX = 0;
  private static final int BILLING_TYPE_IDX = 1;
  private static final int DATE_OF_CREATION_IDX = 2;
  private static final int MONEY_IDX = 3;
  private static final int DATE_OF_EDITION_IDX = 4;
  private static final int CONFIRMED_IDX = 5;
  private static final int DATE_OF_CONFIRM_IDX = 6;
  private static final int PERSON_ASSIGNED_IDX = 7;


  @Override
  public List<BillingForm> filterBillingFormList(final List<BillingForm> oldBillingForm,
      final BillingFilter billingFilter) {
    List<BillingForm> filtered = oldBillingForm
        .stream()
        .filter(bf -> {
          if ("all".equals(billingFilter.getBillingType())) {
            return true;
          } else if ((BillingType.INCOME == BillingType.valueOf(billingFilter.getBillingType()))
              && BillingType.valueOf(billingFilter.getBillingType()) == bf.getBillingType()) {
            return true;
          } else {
            return BillingType.OUTGO == BillingType.valueOf(billingFilter.getBillingType()) &&
                BillingType.valueOf(billingFilter.getBillingType()) == bf.getBillingType();
          }
        })
        .filter(bf -> bf.getPersonAssigned().getName().equals(billingFilter.getPersonName()))
        .filter(bf -> bf.isConfirmed() == billingFilter.isConfirmed())
        .filter(bf -> bf.getDateOfCreation().isAfter(billingFilter.getDateCreationStart()) && bf
            .getDateOfCreation().isBefore(billingFilter.getDateCreationEnd()))
        .collect(Collectors.toList());
    return filtered;
  }

  @Override
  public byte[] generateSheetForBillings(final List<BillingForm> billingForms) throws IOException {
    XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
    XSSFSheet sheet = xssfWorkbook.createSheet("BillingsList");
    Row row = sheet.createRow(0);
    headersForSheet(row);
    int rowCount = 1;
    for (BillingForm billingForm : billingForms) {
      row = sheet.createRow(rowCount++);
      Cell cell = row.createCell(TITLE_IDX);
      cell.setCellValue(billingForm.getTitle());
      cell = row.createCell(BILLING_TYPE_IDX);
      cell.setCellValue(billingForm.getBillingType().toString());
      cell = row.createCell(DATE_OF_CREATION_IDX);
      cell.setCellValue(billingForm.getDateOfCreation().toString());
      cell = row.createCell(MONEY_IDX);
      cell.setCellValue(billingForm.getMoney().doubleValue());
      cell = row.createCell(DATE_OF_EDITION_IDX);
      if (billingForm.getDateOfEdition() != null) {
        cell.setCellValue(billingForm.getDateOfEdition().toString());
      }
      cell = row.createCell(CONFIRMED_IDX);
      cell.setCellValue(billingForm.isConfirmed());
      cell = row.createCell(DATE_OF_CONFIRM_IDX);
      if (billingForm.getDateOfConfirm() != null) {
        cell.setCellValue(billingForm.getDateOfConfirm().toString());
      }
      cell = row.createCell(PERSON_ASSIGNED_IDX);
      cell.setCellValue(billingForm.getPersonAssigned().getName());
    }
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    xssfWorkbook.write(byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }

  private void headersForSheet(final Row row) {
    Cell cell = row.createCell(0);
    cell.setCellValue("Title");
    cell = row.createCell(1);
    cell.setCellValue("Type");
    cell = row.createCell(2);
    cell.setCellValue("Created");
    cell = row.createCell(3);
    cell.setCellValue("Money");
    cell = row.createCell(4);
    cell.setCellValue("Edited");
    cell = row.createCell(5);
    cell.setCellValue("is confirmed? ");
    cell = row.createCell(6);
    cell.setCellValue("Confirmed date");
    cell = row.createCell(7);

    cell.setCellValue("Person");
  }
}




