/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.controllers.form;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Option;
import com.trenako.web.images.validation.Image;

/**
 * @author Carlo Micieli
 */
public class OptionForm {

    @Valid
    private Option option;

    @Image(message = "option.image.notValid")
    private MultipartFile file;

    public OptionForm() {
        option = new Option();
    }

    public static OptionForm newForm() {
        return new OptionForm();
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Option buildOption() throws IOException {
        return new Option(getOption().getName(),
                getOption().getFamily(),
                getFile().getBytes());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof OptionForm)) return false;

        OptionForm other = (OptionForm) obj;
        return new EqualsBuilder()
                .append(this.option, other.option)
                .append(this.file, other.file)
                .isEquals();
    }
}
