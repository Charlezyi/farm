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
package com.zerocracy.farm.reactive;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.zerocracy.farm.MismatchException;
import com.zerocracy.jstk.Project;
import com.zerocracy.jstk.Stakeholder;
import com.zerocracy.jstk.farm.fake.FkProject;
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StkSmart}.
 * @author Kirill (g4s8.public@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.19
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class StkSmartTest {

    @Test
    public void doesntDuplicateMistakes() throws Exception {
        final AtomicInteger hits = new AtomicInteger();
        final Stakeholder stk = new StkSmart(
            (project, xml) -> {
                hits.incrementAndGet();
                throw new MismatchException("oops");
            }
        );
        final XML claim = new XMLDocument(
            "<claim><type>test</type></claim>"
        ).nodes("/claim").get(0);
        final Project project = new FkProject();
        stk.process(project, claim);
        stk.process(project, claim);
        MatcherAssert.assertThat(hits.get(), Matchers.equalTo(1));
    }

    @Test
    public void passesNormalCallsThrough() throws Exception {
        final AtomicInteger hits = new AtomicInteger();
        final Stakeholder stk = new StkSmart(
            (project, xml) -> {
                hits.incrementAndGet();
            }
        );
        final XML claim = new XMLDocument(
            "<claim><type>test me</type></claim>"
        ).nodes("/claim ").get(0);
        final Project project = new FkProject();
        stk.process(project, claim);
        stk.process(project, claim);
        MatcherAssert.assertThat(hits.get(), Matchers.equalTo(2));
    }

}