<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <!-- Attribute used for table border -->
  <xsl:attribute-set name="tableBorder">
    <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
  </xsl:attribute-set>
  <xsl:template match="BillingRaportSchema">
    <fo:root>
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4"
          page-height="29.7cm" page-width="21.0cm" margin="1cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="15pt" font-family="Helvetica" color="Black" font-weight="bold"
            space-after="15pt">
            Listed all billings for Event:
            <xsl:value-of select="eventName"/>
          </fo:block>
          <fo:block font-size="12pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>

              <fo:table-header>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Company Name</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Money</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Was paid?</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Person assigned</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Paid time</fo:block>
                </fo:table-cell>
              </fo:table-header>
              <fo:table-body>
                <xsl:apply-templates select="billing"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block font-size="15pt" font-family="Helvetica" color="Black" font-weight="bold"
            space-before="50pt" space-after="5pt">
            Summary:
          </fo:block>
          <fo:block font-size="12pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-header>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Num of not paid</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Num of paid</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Coast of all paid</fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="tableBorder">
                  <fo:block font-weight="bold">Coast of all not paid</fo:block>
                </fo:table-cell>
              </fo:table-header>
              <fo:table-body>
                <xsl:apply-templates select="billingsSummary"/>
              </fo:table-body>
            </fo:table>
          </fo:block>

        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>
  <xsl:template match="billing">
    <fo:table-row>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="companyName"/>
        </fo:block>
      </fo:table-cell>

      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="money"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="paided"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="personAssigned"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="paidedTime"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
  <xsl:template match="billingsSummary">
    <fo:table-row>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="notPaided"/>
        </fo:block>
      </fo:table-cell>

      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="paided"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="coastOfAllPaided"/>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="tableBorder">
        <fo:block>
          <xsl:value-of select="coastOfAllNotPaided"/>
        </fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>