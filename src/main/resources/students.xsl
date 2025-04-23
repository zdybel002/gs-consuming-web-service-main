<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://task2/student"
                version="1.0">

    <xsl:output method="xml" encoding="UTF-8"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simple" page-height="11in" page-width="8.5in">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="simple">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="Helvetica" font-size="22pt" font-weight="bold" text-align="center" color="#4CAF50" margin-bottom="20pt">
                        Students List
                    </fo:block>

                    <xsl:for-each select="ns2:getAllStudentsResponse/ns2:student">
                        <fo:block font-family="Arial, sans-serif" font-size="12pt" margin-top="10pt" line-height="1.6" border-bottom="1px solid #D3D3D3" padding-bottom="10pt" keep-together="always">
                            <fo:block font-weight="bold" color="#2C3E50">
                                Student ID: <xsl:value-of select="ns2:id"/>
                            </fo:block>
                            <fo:block font-weight="bold" color="#2C3E50">
                                Name: <xsl:value-of select="ns2:name"/>
                            </fo:block>
                            <fo:block font-weight="bold" color="#2C3E50">
                                Courses:
                            </fo:block>
                            <xsl:for-each select="ns2:courses">
                                <fo:block font-style="italic" color="#7F8C8D">
                                    - <xsl:value-of select="ns2:title"/>
                                </fo:block>
                            </xsl:for-each>
                            <fo:block space-after="15pt"/>
                        </fo:block>
                    </xsl:for-each>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
