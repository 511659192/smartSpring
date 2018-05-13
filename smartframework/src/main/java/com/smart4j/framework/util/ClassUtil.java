package com.smart4j.framework.util;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by ym on 2018/5/11.
 */
@Slf4j
public class ClassUtil {

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String name, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(name, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     *
     * @param packageName 包路径
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = Sets.newHashSet();
        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url == null) {
                    continue;
                }
                String protocol = url.getProtocol();
                if (StringUtils.equals("file", protocol)) {
                    String packagePath = url.getPath().replaceAll("%20", "");
                    addClass(classSet, packagePath, packageName.replaceAll("/", "."));
                } else if (StringUtils.equals("jar", protocol)) {
                    URLConnection urlConnection = url.openConnection();
                    if (urlConnection == null) {
                        continue;
                    }
                    JarFile jarFile = ((JarURLConnection) urlConnection).getJarFile();
                    if (jarFile == null) {
                        continue;
                    }
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        if (StringUtils.endsWith(jarEntryName, ".class")) {
                            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                            doAddClass(classSet, className);
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> clazz = loadClass(className, false);
        classSet.add(clazz);
    }

    /**
     *
     * @param classSet
     * @param packagePath 包路径 /分割
     * @param packageName 包名 .分割
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && StringUtils.endsWith(file.getName(), ".class")) || file.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotBlank(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotBlank(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotBlank(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }
}
