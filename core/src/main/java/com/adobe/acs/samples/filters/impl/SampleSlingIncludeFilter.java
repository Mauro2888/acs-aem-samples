/*
 * #%L
 * ACS AEM Samples
 * %%
 * Copyright (C) 2015 Adobe
 * %%
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
 * #L%
 */

package com.adobe.acs.samples.filters.impl;

import com.day.cq.wcm.api.WCMMode;
import com.day.cq.wcm.api.components.IncludeOptions;
import org.apache.sling.servlets.annotations.SlingServletFilter;
import org.apache.sling.servlets.annotations.SlingServletFilterScope;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Sample implementation of a Sling Filter that remove decoration from cq:includes
 */

@Component
// See documentation for the @SlingServletFilter configuration here: https://sling.apache.org/documentation/the-sling-engine/filters.html
@SlingServletFilter(
        scope = {SlingServletFilterScope.INCLUDE}, // REQUEST, INCLUDE, FORWARD, ERROR, COMPONENT (REQUEST, INCLUDE, COMPONENT)
        pattern = "/content/.*",
        extensions = {"html"},
        methods = {"GET"}
)
public class SampleSlingIncludeFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SampleSlingIncludeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Usually, do nothing
    }

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        final WCMMode mode = WCMMode.fromRequest(request);
        final IncludeOptions includeOptions = IncludeOptions.getOptions(request, true);

        // Only execute in Publish mode
        if (false && includeOptions != null &&  ((mode == null || WCMMode.DISABLED.equals(mode)))) {
            // Disable CQ Decoration on cq:includes or sling:includes, only in Publish mode
            includeOptions.setDecorationTagName("");
        }

        // Finally, proceed with the the Filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Usually, do Nothing
    }
}