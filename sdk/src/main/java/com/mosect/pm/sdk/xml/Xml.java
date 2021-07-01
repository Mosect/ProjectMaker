package com.mosect.pm.sdk.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Xml {

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private TransformerFactory transformerFactory;
    private Transformer transformer;

    public Xml() throws ParserConfigurationException, TransformerConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();

        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        // 换行
        transformer.setOutputProperty(OutputKeys.INDENT, "YES");
        // 文档字符编码
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
    }

    public Document parse(String text) throws IOException, SAXException {
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(text));
        return documentBuilder.parse(source);
    }

    public Document create() {
        return documentBuilder.newDocument();
    }

    public String toXml(Document document) throws TransformerException {
        DOMSource source = new DOMSource(document);
        StringWriter writer = new StringWriter();
        transformer.transform(source, new StreamResult(writer));
        return writer.toString();
    }
}
