/**
 * Copyright (c) 2016-2017 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.pm.staff.voters;

import com.zerocracy.jstk.fake.FkProject;
import com.zerocracy.pmo.People;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Vacation}.
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class VacationTest {

    @Test
    public void highRankForVacationTest() throws Exception {
        final FkProject project = new FkProject();
        final String uid = "g4s8";
        final People people = new People(project).bootstrap();
        people.invite(uid, uid);
        people.vacation(uid, true);
        MatcherAssert.assertThat(
            new Vacation(project).vote(uid, new StringBuilder()),
            Matchers.equalTo(1.0D)
        );
    }
}
