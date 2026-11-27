package com.jaesung.auction.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaskingUtilTest {

    @Test
    void 첫_글자만_남기고_가린다() {
        assertThat(MaskingUtil.mask("jaesung")).isEqualTo("j**");
        assertThat(MaskingUtil.mask("심재성")).isEqualTo("심**");
    }

    @Test
    void null은_null_빈문자열은_빈문자열() {
        assertThat(MaskingUtil.mask(null)).isNull();
        assertThat(MaskingUtil.mask("")).isEmpty();
    }
}
