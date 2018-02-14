/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.crowninteractive.wfmworker.misc;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import javax.persistence.Id;

/**
 *
 * @author osita
 */
public class AnnotationExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null || f.getAnnotation(Id.class) != null || f.getName().equals("userId");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
