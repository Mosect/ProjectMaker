package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;
import com.mosect.pm.sdk.util.TextUtils;
import com.mosect.pm.sdk.xml.Xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

public class ElementXml extends ElementText {

    private Document document;
    private Xml xml;

    public ElementXml(DataSet dataSet, DataEntry dataEntry, String[] args) {
        super(dataSet, dataEntry, args);
        try {
            xml = new Xml();
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
        if (null == document) {
            super.setText(null);
        } else {
            try {
                String text = xml.toXml(document);
                super.setText(text);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setText(String text) {
        if (TextUtils.empty(text)) {
            document = null;
        } else {
            try {
                document = xml.parse(text);
            } catch (IOException | SAXException e) {
                throw new RuntimeException(e);
            }
        }
        super.setText(text);
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws IOException {
        try {
            String text = xml.toXml(document);
            super.setText(text);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        super.onWrite(outputStream);
    }
}
