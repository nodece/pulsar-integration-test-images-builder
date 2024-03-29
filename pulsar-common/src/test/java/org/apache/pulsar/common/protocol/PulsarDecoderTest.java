/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.pulsar.common.protocol;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.apache.pulsar.common.api.proto.CommandActiveConsumerChange;
import org.testng.annotations.Test;

/**
 * Unit test of {@link PulsarDecoder}.
 */
public class PulsarDecoderTest {
    @Test
    public void testChannelRead() throws Exception {
        long consumerId = 1234L;
        ByteBuf changeBuf = Commands.newActiveConsumerChange(consumerId, true);
        ByteBuf cmdBuf = changeBuf.slice(4, changeBuf.writerIndex() - 4);
        PulsarDecoder decoder = spy(new PulsarDecoder() {
            @Override
            protected void handleActiveConsumerChange(CommandActiveConsumerChange change) {
            }

            @Override
            protected void messageReceived() {
            }
        });
        decoder.channelRead(mock(ChannelHandlerContext.class), cmdBuf);
        verify(decoder, times(1)).handleActiveConsumerChange(any(CommandActiveConsumerChange.class));
    }
}
