package com.example.anonimo1.xmlapp1;

import com.example.anonimo1.xmlapp1.Noticia;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import java.net.MalformedURLException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Javier Benítez del Pozo on 12/03/2016.
 */
public class RssParserSax {
    private URL rssUrl;

    public RssParserSax(String url)
    {
        try
        {
            this.rssUrl = new URL(url);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<Noticia> parse()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try
        {
            SAXParser parser = factory.newSAXParser();
            RssHandler handler = new RssHandler();
            parser.parse(this.getInputStream(), handler);
            return handler.getNoticias();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream()
    {
        try
        {
            return rssUrl.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}