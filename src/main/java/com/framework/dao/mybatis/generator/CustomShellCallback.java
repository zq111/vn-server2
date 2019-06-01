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

import static org.mybatis.generator.api.dom.OutputUtilities.newLine;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.exception.ShellException;

/**
 * The Class DefaultShellCallback.
 *
 * @author Jeff Butler
 */

/**
 * 原有Java文件的合并直接抛出了异常
 * 此处扩展合并规则
 */
public class CustomShellCallback implements ShellCallback {

    /** The overwrite. */
    private boolean overwrite = false;

    /**
     * Instantiates a new default shell callback.
     */
    public CustomShellCallback() {
        super();
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#getDirectory(java.lang.String, java.lang.String)
     */
    @Override
    public File getDirectory(String targetProject, String targetPackage)
            throws ShellException {
        // targetProject is interpreted as a directory that must exist
        //
        // targetPackage is interpreted as a sub directory, but in package
        // format (with dots instead of slashes). The sub directory will be
        // created
        // if it does not already exist

        File project = new File(targetProject);
        if (!project.isDirectory()) {
            throw new ShellException(getString("Warning.9", //$NON-NLS-1$
                    targetProject));
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new ShellException(getString("Warning.10", //$NON-NLS-1$
                        directory.getAbsolutePath()));
            }
        }

        return directory;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#refreshProject(java.lang.String)
     */
    @Override
    public void refreshProject(String project) {
        // nothing to do in the default shell callback
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#isMergeSupported()
     */
    @Override
    public boolean isMergeSupported() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#isOverwriteEnabled()
     */
    @Override
    public boolean isOverwriteEnabled() {
        return overwrite;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.ShellCallback#mergeJavaFile(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
     */
    @Override
    public String mergeJavaFile(String newFileSource,
                                File existingFile, String[] javadocTags, String fileEncoding) {

        String fileNotes = "";
        try {
            fileNotes = getNewJavaFile(newFileSource,existingFile.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileNotes;
    }

    public String getNewJavaFile(String newFileSource, String existingFileFullPath) throws FileNotFoundException {
        CompilationUnit newCompilationUnit = JavaParser.parse(newFileSource);
        CompilationUnit existingCompilationUnit = JavaParser.parse(new File(existingFileFullPath));
        return mergerFile(newCompilationUnit,existingCompilationUnit);
    }

    public String mergerFile(CompilationUnit newCompilationUnit,CompilationUnit existingCompilationUnit){

        String tab = "    ";

        StringBuilder sb = new StringBuilder(newCompilationUnit.getPackageDeclaration().get().toString());
        newCompilationUnit.removePackageDeclaration();

        //合并imports
        NodeList<ImportDeclaration> imports = newCompilationUnit.getImports();
        imports.addAll(existingCompilationUnit.getImports());
        Set importSet = new HashSet<ImportDeclaration>();
        importSet.addAll(imports);

        NodeList<ImportDeclaration> newImports = new NodeList<>();
        newImports.addAll(importSet);
        newCompilationUnit.setImports(newImports);
        for (ImportDeclaration i:newCompilationUnit.getImports()) {
            sb.append(i.toString());
        }
        newLine(sb);
        NodeList<TypeDeclaration<?>> types = newCompilationUnit.getTypes();
        NodeList<TypeDeclaration<?>> oldTypes = existingCompilationUnit.getTypes();

        for (int i = 0;i<types.size();i++) {
            //截取Class
            String classNameInfo = types.get(i).toString().substring(0, types.get(i).toString().indexOf("{")+1);
            sb.append(classNameInfo);
            newLine(sb);
            newLine(sb);
            //合并fields
            List<FieldDeclaration> fields = types.get(i).getFields();
            List<FieldDeclaration> oldFields = oldTypes.get(i).getFields();
            List<FieldDeclaration> newFields = new ArrayList<>();
            HashSet<FieldDeclaration> fieldDeclarations = new HashSet<>();
            fieldDeclarations.addAll(fields);
            fieldDeclarations.addAll(oldFields);
            newFields.addAll(fieldDeclarations);
            for (FieldDeclaration f: newFields){
                sb.append(f.toString());
                newLine(sb);
                newLine(sb);
            }

            //合并methods
            List<MethodDeclaration> methods = types.get(i).getMethods();
            List<MethodDeclaration> existingMethods = oldTypes.get(i).getMethods();
            for (MethodDeclaration f: methods){
                sb.append(tab);
                sb.append(f.toString());
                newLine(sb);
                newLine(sb);
            }
            for (MethodDeclaration m:existingMethods){
                boolean flag = true;
                for (String tag : MergeConstants.OLD_ELEMENT_TAGS) {
                    if (m.toString().contains(tag)) {
                        flag = false;
                        break;
                    }
                }
                if (flag){
                    sb.append(tab);
                    sb.append(m.toString());
                    newLine(sb);
                    newLine(sb);
                }
            }

            //判断是否有内部类
            types.get(i).getChildNodes();
            for (Node n:types.get(i).getChildNodes()){
                if (n.toString().contains("static class")){
                    sb.append(n.toString());
                }
            }

        }

        return sb.append("}").toString();
    }


}
