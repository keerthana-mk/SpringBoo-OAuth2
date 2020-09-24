
select * from user;

select * from permission_role;


update oauth_client_details set client_secret = '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi' where client_id = 'sample-client-id';
update oauth_client_details set authorized_grant_types='password,authorization_code' where client_id = 'sample-client-id';


update user set password = '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi' where username = 'madhavan';

select * from user;

commit;

select * from oauth_client_details;