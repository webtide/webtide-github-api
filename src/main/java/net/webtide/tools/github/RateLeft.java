//
// ========================================================================
// Copyright (c) Webtide LLC and others.
//
// This program and the accompanying materials are made available under the
// terms of the Apache License, Version 2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: Apache-2.0
// ========================================================================
//

package net.webtide.tools.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateLeft
{
    private static final Logger LOG = LoggerFactory.getLogger(RateLeft.class);

    // RateLimits
    //   [rate=Rate[u:75/l:5000(r:4925),reset=3,018s],
    //   [{
    //    core=Rate[u:75/l:5000(r:4925),reset=3,018s],
    //    search=Rate[u:0/l:30(r:30),reset=60s],
    //    graphql=Rate[u:1/l:5000(r:4999),reset=2,429s],
    //    integration_manifest=Rate[u:0/l:5000(r:5000),reset=3,600s],
    //    source_import=Rate[u:0/l:100(r:100),reset=60s],
    //    code_scanning_upload=Rate[u:0/l:500(r:500),reset=3,600s]}]]

    private final RateLimits limits;

    public RateLeft(RateLimits rateLimits)
    {
        this.limits = rateLimits;
    }

    public int applyRequest(String type)
    {
        RateLimits.Rate rate = this.limits.getResourceLimit(type);
        if (rate == null)
            throw new RuntimeException("Unrecognized Rate Limit Type: " + type);
        rate.remaining--;
        if (rate.remaining <= 10)
        {
            // we need to block requests until the reset time
            long waitMs = (System.currentTimeMillis() - rate.reset) + 1000;
            LOG.warn("Rate Limit applied.  Waiting {} ms", waitMs);
            try
            {
                Thread.sleep(waitMs);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return rate.remaining;
    }

    public boolean isExpired()
    {
        return System.currentTimeMillis() > this.limits.rate.reset;
    }
}
