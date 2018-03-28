package com.king.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 金丹 on 2017/12/22.
 */
public class OrderedProperties extends Properties {
    private transient Map mapDelegate = null;
    private transient List listDelegate = null;

    public OrderedProperties() {
    }

    public synchronized Object put(Object key, Object value) {
        if(this.mapDelegate != null) {
            return this.mapDelegate.put(key, value);
        } else if(this.listDelegate != null) {
            this.listDelegate.add(new AbstractMap.SimpleEntry(key, value));
            return value;
        } else {
            return super.put(key, value);
        }
    }

    public void loadMap(InputStream in, Map map) throws IOException {
        if(map instanceof Properties) {
            ((Properties)map).load(in);
        } else if(map != null) {
            synchronized(this) {
                Map prev = this.mapDelegate;

                try {
                    this.mapDelegate = map;
                    this.load(in);
                } finally {
                    this.mapDelegate = prev;
                }
            }
        }

    }

    public void loadList(InputStream in, List list) throws IOException {
        if(list instanceof Properties) {
            ((Properties)list).load(in);
        } else if(list != null) {
            synchronized(this) {
                List prev = this.listDelegate;

                try {
                    this.listDelegate = list;
                    this.load(in);
                } finally {
                    this.listDelegate = prev;
                }
            }
        }

    }
}

