package mk.learning.controllers;

import lombok.AllArgsConstructor;
import mk.learning.dao.OAuthClientDetails;
import mk.learning.dao.Permission;
import mk.learning.dao.Role;
import mk.learning.dao.User;
import mk.learning.model.AssignPermissionToRoleRequest;
import mk.learning.model.AssignRoleToUserRequest;
import mk.learning.repository.OAuthClientDetailsRepo;
import mk.learning.repository.PermissionRepo;
import mk.learning.repository.RoleRepo;
import mk.learning.repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class DbController {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PermissionRepo permissionRepo;
    private OAuthClientDetailsRepo oAuthClientDetailsRepo;

    @PostMapping(value = "/addUser")
    public User addUserPostController(@RequestBody User user) {
        user.setEnabled(true);
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        user.setCredentialsExpired(false);
        return userRepo.save(user);
    }

    @PostMapping(value = "/addPermission")
    public Permission addPermissionPostController(@RequestBody Permission permission) {
        return permissionRepo.save(permission);
    }

    @PostMapping(value = "/addClient")
    public OAuthClientDetails addOauthClientPostController(@RequestBody OAuthClientDetails oAuthClientDetails) {
        return oAuthClientDetailsRepo.save(oAuthClientDetails);
    }

    @PostMapping(value = "/addRole")
    public Role addRolePostController(@RequestBody Role role) {
        return roleRepo.save(role);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public void deleteUserController(@PathVariable("id") int userId) {
        userRepo.deleteById(userId);
    }

    @DeleteMapping(value = "/deletePermission/{id}")
    public void deletePermissionController(@PathVariable("id") int permissionId) {
        permissionRepo.deleteById(permissionId);
    }

    @DeleteMapping(value = "/deleteRole/{id}")
    public void deleteRoleController(@PathVariable("id") int roleId) {
        permissionRepo.deleteById(roleId);
    }

    @DeleteMapping(value = "/deleteClient/{id}")
    public void deleteClientController(@PathVariable("id") String clientId) {
        oAuthClientDetailsRepo.deleteById(clientId);
    }

    @GetMapping(value = "/getAllUsers")
    public Iterable<User> getAllUsersController() {
        return userRepo.findAll();
    }

    @GetMapping(value = "/getAllPermissions")
    public Iterable<Permission> getAllPermissionsController() {
        return permissionRepo.findAll();
    }

    @GetMapping(value = "/getAllRoles")
    public Iterable<Role> getAllRolesController() {
        return roleRepo.findAll();
    }

    @GetMapping(value = "/getAllClients")
    public Iterable<OAuthClientDetails> getAllClientsController() {
        return oAuthClientDetailsRepo.findAll();
    }

    @PostMapping(value = "/assignRoleToUser")
    public ResponseEntity assignRoleToUserPostController(@RequestBody AssignRoleToUserRequest request) {
        Optional<User> userOpt = userRepo.findById(request.getUserId());
        Optional<Role> roleOpt = roleRepo.findById(request.getRoleId());
        if (!userOpt.isPresent() || !roleOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Role or User not found");
        }
        User user = userOpt.get();
        Role role = roleOpt.get();
        user.getUserRoles().add(role);
        role.getUsers().add(user);
        userRepo.save(user);
        return ResponseEntity.ok("added successfully");
    }

    @PostMapping(value = "/assignPermissionToRole")
    public ResponseEntity assignPermissionToRolePostController(@RequestBody AssignPermissionToRoleRequest request) {
        Optional<Permission> permissionOpt = permissionRepo.findById(request.getPermissionId());
        Optional<Role> roleOpt = roleRepo.findById(request.getRoleId());
        if (!permissionOpt.isPresent() || !roleOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Permission or Role not found");
        }
        Permission permission = permissionOpt.get();
        permission.getRoles().add(roleOpt.get());
        permissionRepo.save(permission);
        return ResponseEntity.ok("added successfully");
    }
}
