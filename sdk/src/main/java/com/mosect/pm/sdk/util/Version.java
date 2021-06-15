package com.mosect.pm.sdk.util;

/**
 * 版本号的格式为 X.Y.Z(又称 Major.Minor.Patch)，递增的规则为：
 * <p>
 * X 表示主版本号，当 API 的兼容性变化时，X 需递增。
 * Y 表示次版本号，当增加功能时(不影响 API 的兼容性)，Y 需递增。
 * Z 表示修订号，当做 Bug 修复时(不影响 API 的兼容性)，Z 需递增。
 * 详细的规则如下：
 * <p>
 * X, Y, Z 必须为非负整数，且不得包含前导零，必须按数值递增，如 1.9.0 -> 1.10.0 -> 1.11.0
 * 0.Y.Z 的版本号表明软件处于初始开发阶段，意味着 API 可能不稳定；1.0.0 表明版本已有稳定的 API。
 * 当 API 的兼容性变化时，X 必须递增，Y 和 Z 同时设置为 0；当新增功能(不影响 API 的兼容性)或者 API 被标记为 Deprecated 时，Y 必须递增，同时 Z 设置为 0；当进行 bug fix 时，Z 必须递增。
 * 先行版本号(Pre-release)意味该版本不稳定，可能存在兼容性问题，其格式为：X.Y.Z.[a-c][正整数]，如 1.0.0.a1，1.0.0.b99，1.0.0.c1000。
 * 开发版本号常用于 CI-CD，格式为 X.Y.Z.dev[正整数]，如 1.0.1.dev4。
 * 版本号的排序规则为依次比较主版本号、次版本号和修订号的数值，如 1.0.0 < 1.0.1 < 1.1.1 < 2.0.0；对于先行版本号和开发版本号，有：1.0.0.a100 < 1.0.0，2.1.0.dev3 < 2.1.0；当存在字母时，以 ASCII 的排序来比较，如 1.0.0.a1 < 1.0.0.b1。
 * 注意：版本一经发布，不得修改其内容，任何修改必须在新版本发布！
 * 一些修饰的词
 * <p>
 * alpha：内部版本
 * beta：测试版
 * demo：演示版
 * enhance：增强版
 * free：自由版
 * full version：完整版，即正式版
 * lts：长期维护版本
 * release：发行版
 * rc：即将作为正式版发布
 * standard：标准版
 * ultimate：旗舰版
 * upgrade：升级版
 * <p>
 * 作者：kaysenyim
 * 链接：https://www.jianshu.com/p/c675121a8bfd
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class Version implements Comparable<Version> {

    private static int getCode(String[] fs, int index) {
        if (index < fs.length) {
            return Integer.parseInt(fs[index]);
        }
        return 0;
    }

    public enum Type {
        /**
         * 开发版本
         */
        DEV("dev", 0),
        /**
         * 内部版本
         */
        ALPHA("alpha", 1),
        /**
         * 测试版本
         */
        BETA("beta", 2),
        /**
         * 发行版
         */
        RELEASE("release", 3);

        private String text;
        private int level;

        Type(String text, int level) {
            this.text = text;
            this.level = level;
        }

        public String getText() {
            return text;
        }

        public int getLevel() {
            return level;
        }

        String getCode(String text) {
            if (text.toLowerCase().startsWith(this.text)) {
                if (text.length() > this.text.length()) {
                    return text.substring(this.text.length());
                }
                return "";
            }
            return text;
        }

        static Type getType(String text) {
            for (Type type : values()) {
                if (text.startsWith(type.text)) {
                    return type;
                }
            }
            return RELEASE;
        }

    }

    private String raw;
    private int major;
    private int minor;
    private int patch;
    private Type type;
    private String ext;
    private Integer extCode;

    public Version(String text) {
        String[] fs = text.split("\\.");
        major = getCode(fs, 0);
        minor = getCode(fs, 1);
        patch = getCode(fs, 2);
        if (fs.length >= 4) {
            String typeText = fs[3].toLowerCase();
            type = Type.getType(typeText);
            ext = type.getCode(typeText);
            try {
                extCode = Integer.parseInt(ext);
            } catch (NumberFormatException ignored) {
            }
        } else {
            type = Type.RELEASE;
            ext = "";
        }
        this.raw = text;
    }

    @Override
    public int compareTo(Version o) {
        int majorCmp = major - o.major;
        if (majorCmp == 0) {
            int minorCmp = minor - o.minor;
            if (minorCmp == 0) {
                int patchCmp = patch - o.patch;
                if (patchCmp == 0) {
                    int typeCmp = type.getLevel() - o.type.getLevel();
                    if (typeCmp == 0) {
                        // ext字符串右对齐比较
                        if (null != extCode && null != o.extCode) {
                            return extCode - o.extCode;
                        }
                        int len1 = null == ext ? 0 : ext.length();
                        int len2 = null == o.ext ? 0 : o.ext.length();
                        if (len1 > 0) {
                            if (len2 > 0) {
                                return ext.compareTo(o.ext);
                            }
                            return -1;
                        } else {
                            if (len2 > 0) {
                                return 1;
                            }
                            return 0;
                        }
                    }
                    return typeCmp;
                }
                return patchCmp;
            }
            return minorCmp;
        }
        return majorCmp;
    }

    @Override
    public String toString() {
        return getRaw();
    }

    public String getRaw() {
        return raw;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public Type getType() {
        return type;
    }

    public String getExt() {
        return ext;
    }

    public Integer getExtCode() {
        return extCode;
    }
}
