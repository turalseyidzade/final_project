package azcompany.final_projeckt.mapper;

import azcompany.final_projeckt.config.MapperConfig;
import azcompany.final_projeckt.dto.user.UserRegistrationRequestDto;
import azcompany.final_projeckt.dto.user.UserResponseDto;
import azcompany.final_projeckt.dao.entities.User;
import org.mapstruct.Mapper;

@Mapper(config =  MapperConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User user);

    User toUser(UserRegistrationRequestDto requestDto);
}
