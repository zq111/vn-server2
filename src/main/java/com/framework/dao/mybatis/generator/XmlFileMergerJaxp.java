package com.framework.dao.mybatis.generator;

/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xerces.internal.dom.DeferredTextImpl;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DomWriter;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class handles the task of merging changes into an existing XML file.
 *
 * @author Jeff Butler
 */

/**
 * 合并xml工具,改变原有合并逻辑
 */
public class XmlFileMergerJaxp {
    private static class NullEntityResolver implements EntityResolver {
        /**
         * returns an empty reader. This is done so that the parser doesn't
         * attempt to read a DTD. We don't need that support for the merge and
         * it can cause problems on systems that aren't Internet connected.
         */
        @Override
        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException {

            StringReader sr = new StringReader(""); //$NON-NLS-1$

            return new InputSource(sr);
        }
    }

    /**
     * Utility class - no instances allowed
     */
    private XmlFileMergerJaxp() {
        super();
    }

    public static String getMergedSource(GeneratedXmlFile generatedXmlFile,
                                         File existingFile) throws ShellException {

        try {
            return getMergedSource(new InputSource(new StringReader(generatedXmlFile.getFormattedContent())),
                    new InputSource(new InputStreamReader(new FileInputStream(existingFile), "UTF-8")), //$NON-NLS-1$
                    existingFile.getName());
        } catch (IOException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        } catch (SAXException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        } catch (ParserConfigurationException e) {
            throw new ShellException(getString("Warning.13", //$NON-NLS-1$
                    existingFile.getName()), e);
        }
    }

    public static String getMergedSource(InputSource newFile,
                                         InputSource existingFile, String existingFileName) throws IOException, SAXException,
            ParserConfigurationException, ShellException {

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        factory.setExpandEntityReferences(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new NullEntityResolver());

        Document existingDocument = builder.parse(existingFile);
        Document newDocument = builder.parse(newFile);

        DocumentType newDocType = newDocument.getDoctype();
        DocumentType existingDocType = existingDocument.getDoctype();

        if (!newDocType.getName().equals(existingDocType.getName())) {
            throw new ShellException(getString("Warning.12", //$NON-NLS-1$
                    existingFileName));
        }

        Element existingRootElement = existingDocument.getDocumentElement();
        Element newRootElement = newDocument.getDocumentElement();

        //获取新生成的所有xml，所有element的id列表，删除之前同名的结点
        NodeList newMethods = newRootElement.getChildNodes();
        List<String> methods = new ArrayList<>();
        for (int i = 0; i < newMethods.getLength(); i++) {
            Node node = newMethods.item(i);
            try {
                if (node instanceof DeferredTextImpl) {
                    continue;
                }
                Element ele = (Element) node;
                methods.add(ele.getAttribute("id"));
            } catch (Exception e) {
                //#text节点转换会异常
                continue;
            }
            if (i == newMethods.getLength() - 1) {
                if (isWhiteSpace(node)) {
                    break;
                }
            }
        }

        // reconcile the root element attributes -
        // take all attributes from the new element and add to the existing
        // element

        // remove all attributes from the existing root element
        NamedNodeMap attributes = existingRootElement.getAttributes();
        int attributeCount = attributes.getLength();
        for (int i = attributeCount - 1; i >= 0; i--) {
            Node node = attributes.item(i);
            existingRootElement.removeAttribute(node.getNodeName());
        }

        // add attributes from the new root node to the old root node
        attributes = newRootElement.getAttributes();
        attributeCount = attributes.getLength();
        for (int i = 0; i < attributeCount; i++) {
            Node node = attributes.item(i);
            existingRootElement.setAttribute(node.getNodeName(), node
                    .getNodeValue());
        }

        // remove the old generated elements and any
        // white space before the old nodes
        List<Node> nodesToDelete = new ArrayList<Node>();
        NodeList children = existingRootElement.getChildNodes();
        int length = children.getLength();
        for (int i = 0; i < length; i++) {
            Node node = children.item(i);
            if (isGeneratedNode(node,methods)) {
                nodesToDelete.add(node);
            } else if (isWhiteSpace(node)
                    && isGeneratedNode(children.item(i + 1),methods)) {
                nodesToDelete.add(node);
            }
        }

        for (Node node : nodesToDelete) {
            existingRootElement.removeChild(node);
        }

        // add the new generated elements
        children = newRootElement.getChildNodes();
        length = children.getLength();
        Node firstChild = existingRootElement.getFirstChild();
        for (int i = 0; i < length; i++) {
            Node node = children.item(i);
            // don't add the last node if it is only white space
            if (i == length - 1 && isWhiteSpace(node)) {
                break;
            }

            Node newNode = existingDocument.importNode(node, true);
            if (firstChild == null) {
                existingRootElement.appendChild(newNode);
            } else {
                existingRootElement.insertBefore(newNode, firstChild);
            }
        }

        // pretty print the result
        return prettyPrint(existingDocument);
    }

    private static String prettyPrint(Document document) throws ShellException {
        DomWriter dw = new DomWriter();
        String s = dw.toString(document);
        return s;
    }

    private static boolean isGeneratedNode(Node node,List<String> methods) {
        boolean rc = false;

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String id = element.getAttribute("id"); //$NON-NLS-1$
            if (methods.contains(id)) {
                return true;
            }

            if (id != null) {
                for (String prefix : MergeConstants.OLD_XML_ELEMENT_PREFIXES) {
                    if (id.startsWith(prefix)) {
                        rc = true;
                        break;
                    }
                }
            }

        }
        return rc;
    }

    private static boolean isWhiteSpace(Node node) {
        boolean rc = false;

        if (node != null && node.getNodeType() == Node.TEXT_NODE) {
            Text tn = (Text) node;
            if (tn.getData().trim().length() == 0) {
                rc = true;
            }
        }

        return rc;
    }
}
