/* Inspired by http://unix.stackexchange.com/a/252915 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <spawn.h>
#include <unistd.h>

int main(int argc, char* argv[], char *env[])
{
    int ret, i;
    pid_t child_pid;
    posix_spawn_file_actions_t child_fd_actions;
    if (ret = posix_spawn_file_actions_init (&child_fd_actions))
        perror ("posix_spawn_file_actions_init"), exit(ret);
    if (ret = posix_spawn_file_actions_addopen (&child_fd_actions, 1, "/tmp/foo-log", 
            O_WRONLY | O_CREAT | O_TRUNC, 0644))
        perror ("posix_spawn_file_actions_addopen"), exit(ret);
    char *args[3] = {"echo", "foo", '\0'};
    if (ret = posix_spawnp (&child_pid, "echo", &child_fd_actions, NULL, args, env))
        perror ("posix_spawn"), exit(ret);
    printf("child pid: %d\n", child_pid);
}
