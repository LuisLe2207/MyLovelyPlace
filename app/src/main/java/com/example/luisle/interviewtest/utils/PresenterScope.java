package com.example.luisle.interviewtest.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by LuisLe on 6/30/2017.
 */

@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface PresenterScope {
}
