package com.hrbeu.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class Md2Html {
    public static String md2html(String content){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public static String md2htmlPro(String content){
        //h标签生成id
        Set<Extension> extensionSet = Collections.singleton(HeadingAnchorExtension.create());
        //转化成table的html
        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtensions).build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensionSet).extensions(tableExtensions).attributeProviderFactory(new AttributeProviderFactory() {
            @Override
            public AttributeProvider create(AttributeProviderContext context) {
                return new CustomAttributeProvider();

            }
        }).build();
        return renderer.render(document);
    }
    static class CustomAttributeProvider implements AttributeProvider{

        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if(node instanceof Link){
                attributes.put("target","_blank");
            }
            if(node instanceof TableBlock){
                attributes.put("class","ui called table");
            }
        }
    }
}
