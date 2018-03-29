/**
 * Copyright (c) 2016-2018 Zerocracy
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
package com.zerocracy.pmo;

import com.jcabi.dynamo.mock.H2Data;
import com.jcabi.dynamo.mock.MkRegion;
import com.zerocracy.Project;
import com.zerocracy.farm.fake.FkProject;
import com.zerocracy.pm.ClaimOut;
import com.zerocracy.pm.Claims;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Hint}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.22
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AvoidDuplicateLiterals (600 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HintTest {

    @Test
    public void postsSimpleClaim() throws Exception {
        final Project project = new FkProject();
        final Hint hint = new Hint(
            new MkRegion(
                new H2Data()
                    .with(
                        "0crat-hints",
                        new String[] {"mnemo"},
                        "ttl", "when"
                    )
            ),
            100, 0,
            new ClaimOut()
                .type("Notify user")
                .token("user;yegor256")
                .param("message", "You are fired, my friend!")
        );
        hint.postTo(project);
        hint.postTo(project);
        MatcherAssert.assertThat(
            new Claims(project).bootstrap().iterate(),
            Matchers.iterableWithSize(1)
        );
    }

}
