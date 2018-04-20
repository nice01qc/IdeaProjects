package domain;

import org.apache.ibatis.annotations.Select;

public interface BookMapper {
    @Select("select * from book where id = #{id}")
    Book selectBook(int id);
}
