package mk.learning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignPermissionToRoleRequest {

    private Integer permissionId;
    private Integer roleId;

}
