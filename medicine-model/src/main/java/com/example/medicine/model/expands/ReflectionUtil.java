package com.example.medicine.model.expands;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReflectionUtil {


    public static List<Class<?>> getClasses(String packageName) {
        List classes = new ArrayList();

        boolean recursive = true;

        String packageDirName = packageName.replace('.', '/');
        try {
            Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = (URL) dirs.nextElement();

                String protocol = url.getProtocol();

                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    try {
                        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();

                        Enumeration entries = jar.entries();

                        while (entries.hasMoreElements()) {
                            JarEntry entry = (JarEntry) entries.nextElement();
                            String name = entry.getName();

                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }

                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf(47);

                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }

                                if ((((idx != -1) || (recursive))) &&
                                        (name.endsWith(".class")) && (!(entry.isDirectory()))) {
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    try {
                                        classes.add(Class.forName(packageName + '.' + className));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, List<Class<?>> classes) {
        File dir = new File(packagePath);

        if ((!(dir.exists())) || (!(dir.isDirectory()))) {
            return;
        }

        File[] dirfiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                //(this.recursive) &&
                return (((file.isDirectory())) || (file.getName().endsWith(".class")));
            }

        });
        File[] arrayOfFile1 = dirfiles;
        int i = arrayOfFile1.length;
        for (int j = 0; j < i; ++j) {
            File file = arrayOfFile1[j];

            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath
                        (), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Field[] getClassFields(Class clazz, boolean isFather) {
        if (clazz.equals(Object.class)) {
            return null;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        if (isFather) {
            Field[] afterFields = getClassFields(clazz.getSuperclass(), isFather);
            if (afterFields == null) {
                return declaredFields;
            }

            return ArrayUtil.joinFields(afterFields, declaredFields);
        }
        return declaredFields;
    }

    public static Field[] getClassFields(Class clazz) {
        return getClassFields(clazz, true);
    }
}
