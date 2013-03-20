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

package griffon.core.resources.editors;

import griffon.core.resources.formatters.CalendarFormatter;
import griffon.core.resources.formatters.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static griffon.util.GriffonNameUtils.isBlank;

/**
 * @author Andres Almiray
 * @since 1.3.0
 */
public class CalendarPropertyEditor extends AbstractPropertyEditor {
    protected void setValueInternal(Object value) {
        if (null == value) {
            super.setValueInternal(null);
        } else if (value instanceof CharSequence) {
            handleAsString(String.valueOf(value));
        } else if (value instanceof Calendar) {
            super.setValueInternal((Calendar) value);
        } else if (value instanceof Date) {
            Calendar c = Calendar.getInstance();
            c.setTime((Date) value);
            super.setValueInternal(c);
        } else {
            throw illegalValue(value, Date.class);
        }
    }

    private void handleAsString(String str) {
        try {
            Object value = null;
            if (!isBlank(str)) {
                value = new SimpleDateFormat().parse(str);
                Calendar c = Calendar.getInstance();
                c.setTime((Date) value);
                value = c;
            }
            super.setValueInternal(value);
        } catch (ParseException e) {
            throw illegalValue(str, Date.class, e);
        }
    }

    protected Formatter resolveFormatter() {
        return new CalendarFormatter(getFormat());
    }
}