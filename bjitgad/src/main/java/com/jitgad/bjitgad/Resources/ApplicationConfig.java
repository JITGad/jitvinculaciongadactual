package com.jitgad.bjitgad.Resources;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author jorge
 */

@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
        }
    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.jitgad.bjitgad.AllApis.Activitiestyperesource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Colortyperesource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Detailsimageresource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Gameimageresource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Gameresource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Gametyperesource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Itemsquestionsresource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Questionsresource.class);
        resources.add(com.jitgad.bjitgad.AllApis.Userresource.class);
    } 
}
