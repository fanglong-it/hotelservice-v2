package fiveman.hotelservice.controller;


import fiveman.hotelservice.entities.User;
import fiveman.hotelservice.request.UserRequest;
import fiveman.hotelservice.response.UserResponse;
import fiveman.hotelservice.service.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@Api(tags = "User")
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @ApiOperation(value = "${UserController.getUsers}", response = User.class, authorizations = { @Authorization(value="apiKey") })
    public ResponseEntity<List<UserResponse>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @PostMapping("/login")
    @ApiOperation(value = "${UserController.login}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String login(//
                        @ApiParam("Username") @RequestParam String username, //
                        @ApiParam("Password") @RequestParam String password) {
        return userService.signin(username, password);
    }

    @PostMapping("/signup")
    @ApiOperation( value = "{Usercontroller.signup}")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<UserRequest> signup(@RequestBody UserRequest user){
        return new ResponseEntity<UserRequest>(userService.signup(user), HttpStatus.OK);
    }

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "${UserController.me}", response = UserResponse.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserResponse whoami(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponse.class);
    }






}
