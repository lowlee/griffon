/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.core.resources;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface for resolving resources, with support for the parameterization and internationalization of such resources.
 *
 * @author Andres Almiray
 * @author Alexander Klein
 * @since 1.1.0
 */
public interface ResourceResolver {
    /**
     * Try to resolve the resource.
     *
     * @param key Key to lookup, such as 'sample.SampleModel.icon'
     * @return The resolved resource at the given key for the default locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key    Key to lookup, such as 'sample.SampleModel.icon'
     * @param locale Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, Locale locale) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key  Key to lookup, such as 'sample.SampleModel.icon'
     * @param args Arguments that will be filled in for params within the resource (params look like "{0}" within a
     *             resource, but this might differ between implementations), or null if none.
     * @return The resolved resource at the given key for the default locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, Object[] args) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key    Key to lookup, such as 'sample.SampleModel.icon'
     * @param args   Arguments that will be filled in for params within the resource (params look like "{0}" within a
     *               resource, but this might differ between implementations), or null if none.
     * @param locale Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, Object[] args, Locale locale) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key  Key to lookup, such as 'sample.SampleModel.icon'
     * @param args Arguments that will be filled in for params within the resource (params look like "{0}" within a
     *             resource, but this might differ between implementations), or null if none.
     * @return The resolved resource at the given key for the default locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, List args) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key    Key to lookup, such as 'sample.SampleModel.icon'
     * @param args   Arguments that will be filled in for params within the resource (params look like "{0}" within a
     *               resource, but this might differ between implementations), or null if none.
     * @param locale Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, List args, Locale locale) throws NoSuchResourceException;

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param defaultValue Message to return if the lookup fails
     * @return The resolved resource at the given key for the default locale
     */
    Object resolveResource(String key, Object defaultValue);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param defaultValue Message to return if the lookup fails
     * @param locale       Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     */
    Object resolveResource(String key, Object defaultValue, Locale locale);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{0}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @return The resolved resource at the given key for the default locale
     */
    Object resolveResource(String key, Object[] args, Object defaultValue);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{0}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @param locale       Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     */
    Object resolveResource(String key, Object[] args, Object defaultValue, Locale locale);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{0}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @return The resolved resource at the given key for the default locale
     */
    Object resolveResource(String key, List args, Object defaultValue);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{0}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @param locale       Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     */
    Object resolveResource(String key, List args, Object defaultValue, Locale locale);

    /**
     * Try to resolve the resource.
     *
     * @param key  Key to lookup, such as 'sample.SampleModel.icon'
     * @param args Arguments that will be filled in for params within the resource (params look like "{:key}"
     *             within a resource, but this might differ between implementations), or null if none.
     * @return The resolved resource at the given key for the default locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, Map<String, Object> args) throws NoSuchResourceException;

    /**
     * Try to resolve the resource.
     *
     * @param key    Key to lookup, such as 'sample.SampleModel.icon'
     * @param args   Arguments that will be filled in for params within the resource (params look like "{:key}"
     *               within a resource, but this might differ between implementations), or null if none.
     * @param locale Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     * @throws NoSuchResourceException if no resource is found
     */
    Object resolveResource(String key, Map<String, Object> args, Locale locale) throws NoSuchResourceException;

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{:key}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @return The resolved resource at the given key for the default locale
     */
    Object resolveResource(String key, Map<String, Object> args, Object defaultValue);

    /**
     * Try to resolve the resource. Return default resource if no resource was found.
     *
     * @param key          Key to lookup, such as 'sample.SampleModel.icon'
     * @param args         Arguments that will be filled in for params within the resource (params look like "{:key}"
     *                     within a resource, but this might differ between implementations), or null if none.
     * @param defaultValue Message to return if the lookup fails
     * @param locale       Locale in which to lookup
     * @return The resolved resource at the given key for the given locale
     */
    Object resolveResource(String key, Map<String, Object> args, Object defaultValue, Locale locale);
}
