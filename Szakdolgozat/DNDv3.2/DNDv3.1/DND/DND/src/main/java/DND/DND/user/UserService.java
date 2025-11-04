package DND.DND.user;

public interface UserService
{
    User save(UserDto userDto);

    User findByEmail(String email);
}
