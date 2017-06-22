package calculate_memory_size;

/**
 * 首页日历<br>
 * 
 * 
 * @author winupon
 * 
 */
public enum IndexCalendarType {
    /**
     * 上午
     */
    FORENOON {

        @Override
        public int getValue() {
            return 0;
        }

        @Override
        public String getNameValue() {
            return FORENOON_NAME;
        }

        @Override
        public int getBeginTime() {
            return FORENOON_BEGINTIME;
        }

    },
    /**
     * 下午
     */
    AFTERNOON {

        @Override
        public int getValue() {
            return 1;
        }

        @Override
        public String getNameValue() {
            return AFTERNOON_NAME;
        }

        @Override
        public int getBeginTime() {
            return AFTERNOON_BEGINTIME;
        }

    },
    /**
     * 晚上
     */
    NIGHT {

        @Override
        public int getValue() {
            return 2;
        }

        @Override
        public String getNameValue() {
            return NIGHT_NAME;
        }

        @Override
        public int getBeginTime() {
            return NIGHT_BEGINTIME;
        }

    };

    /**
     * 得到值
     * 
     * @return
     */
    public abstract int getValue();

    public abstract String getNameValue();

    /**
     * 开始时间
     * 
     * @return
     */
    public abstract int getBeginTime();

    private static final String FORENOON_NAME = "上午";
    private static final int FORENOON_BEGINTIME = 0;

    private static final String AFTERNOON_NAME = "下午";
    private static final int AFTERNOON_BEGINTIME = 12;

    private static final String NIGHT_NAME = "晚上";
    private static final int NIGHT_BEGINTIME = 18;
}
