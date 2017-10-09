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
package com.zerocracy.farm.footprint;

import com.mongodb.MongoClient;
import com.zerocracy.jstk.Item;
import com.zerocracy.jstk.Project;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.EqualsAndHashCode;
import org.cactoos.io.LengthOf;
import org.cactoos.io.TeeInput;

/**
 * Footprint project.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.18
 */
@EqualsAndHashCode(of = "origin")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class FtProject implements Project {

    /**
     * Origin project.
     */
    private final Project origin;

    /**
     * Mongo.
     */
    private final MongoClient mongo;

    /**
     * Ctor.
     * @param pkt Project
     * @param client Mongo client
     */
    FtProject(final Project pkt, final MongoClient client) {
        this.origin = pkt;
        this.mongo = client;
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }

    @Override
    public Item acq(final String file) throws IOException {
        Item item = this.origin.acq(file);
        if ("claims.xml".equals(file)) {
            final Path temp = Files.createTempFile("footprint", ".xml");
            final Path before = item.path();
            if (Files.exists(before)) {
                new LengthOf(new TeeInput(item.path(), temp)).value();
            }
            item = new FtItem(this.toString(), item, this.mongo, temp);
        }
        return item;
    }

}