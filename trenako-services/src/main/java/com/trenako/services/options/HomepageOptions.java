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
package com.trenako.services.options;

/**
 * @author Carlo Micieli
 */
public class HomepageOptions {
    private final int numberOfRollingStocks;
    private final int activityStreamSize;

    private final static HomepageOptions DEFAULT_OPTIONS = new HomepageOptions(10, 10);

    public HomepageOptions(int numberOfRollingStocks, int activityStreamSize) {
        this.numberOfRollingStocks = numberOfRollingStocks;
        this.activityStreamSize = activityStreamSize;
    }

    public static HomepageOptions defaultHomepageOptions() {
        return DEFAULT_OPTIONS;
    }

    /**
     * Returns the maximum number of shown rolling stocks
     *
     * @return the number of rolling stocks
     */
    public int getNumberOfRollingStocks() {
        return numberOfRollingStocks;
    }

    /**
     * Returns the maximum size of the activity steam.
     *
     * @return the activity stream size
     */
    public int getActivityStreamSize() {
        return activityStreamSize;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof HomepageOptions)) return false;

        HomepageOptions other = (HomepageOptions) obj;
        return this.numberOfRollingStocks == other.numberOfRollingStocks &&
                this.activityStreamSize == other.activityStreamSize;
    }
}
