package com.egjpa.rkproj.dto.res;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class CommonResDto<T> {
    private String rs;
    private String rd;
    private T payload;

    public CommonResDto() {
    }

    public CommonResDto(final String rs, final String rd, final T payload) {
        this.rs = rs;
        this.rd = rd;
        this.payload = payload;
    }

    public static <T> CommonResDtoBuilder<T> builder() {
        return new CommonResDtoBuilder<>();
    }

    public String getRs() {
        return this.rs;
    }

    public void setRs(final String rs) {
        this.rs = rs;
    }

    public String getRd() {
        return this.rd;
    }

    public void setRd(final String rd) {
        this.rd = rd;
    }

    public T getPayload() {
        return this.payload;
    }

    public void setPayload(final T payload) {
        this.payload = payload;
    }

    public static class CommonResDtoBuilder<T> {
        private String rs;
        private String rd;
        private T payload;

        CommonResDtoBuilder() {
        }

        public CommonResDto<T> build() {
            CommonResDto<T> dto = new CommonResDto<>();
            dto.rs = this.rs;
            dto.rd = this.rd;
            dto.payload = this.payload;
            if (this.rd == null) {
                if ("S".equalsIgnoreCase(dto.rs)) {
                    dto.rd = "Successful";
                } else if ("F".equalsIgnoreCase(dto.rs)) {
                    dto.rd = "Failure";
                } else if ("EX".equalsIgnoreCase(dto.rs)) {
                    dto.rd = "Something went wrong, Please try again!";
                }
            }

            return dto;
        }

        public CommonResDtoBuilder<T> rs(final String rs) {
            this.rs = rs;
            return this;
        }

        public CommonResDtoBuilder<T> rd(final String rd) {
            this.rd = rd;
            return this;
        }

        public CommonResDtoBuilder<T> payload(final T payload) {
            this.payload = payload;
            return this;
        }

        public String toString() {
            return "CommonResDto.CommonResDtoBuilder(rs=" + this.rs + ", rd=" + this.rd + ", payload=" + this.payload + ")";
        }
    }
}
