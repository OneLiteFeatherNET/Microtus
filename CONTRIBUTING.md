Contributing to Microtus
========================

The team at Microtus is happy when you are willing to contribute to our project.
The process to contribute requires some guidelines that you need to follow. 
These help us to improve the project and maintain a consistent style for each contributor

## Use a Personal Fork and not an Organization

If you want to contribute to Microtus, please fork the repository to your personal account and not to an organization.
This is because GitHub does not allow to fork a repository from an organization to another organization. 
If you fork the repository to an organization, you will not be able to create a pull request.

We much prefer to have PRs show as merged, so please do not use repositories
on organizations for PRs.

See <https://github.com/isaacs/github/issues/1681> for more information on the
issue.

## Requirements

To get started with the contributing of change, you will need some additional software.
Most of them can be obtained in (most) package managers on different platforms.

- `git` - Version control system
- A Java 17 or later JDK:
  - [Adoptium](https://adoptium.net/) has builds for most operating systems
  - In a later update Microtus requires JDK 21 to build!

## Understanding Patches

The project uses a patch oriented way to apply changes to the source code from Minestom.
This behavior is similar to the [PaperMC](https://github.com/PaperMC) project. 
In our case we do this not because there a problem with the license, but we want to keep the project as modular as possible.
The modulation means that we can drop or update patches without any problems.

The complete structure of the project relies on patches. So it is important to have a basic acknowledgement of git.
If you are interests how git works there you have a small basic tutorial
<https://git-scm.com/docs/gittutorial>.

Lets set up the project on your local machine

1. Clone your fork to your local machine
2. Open the project with your favorite IDE
3. Type `git submodule update --init` to initialize the submodule
4. Run `./gradlew applyPatches` in the main directory from Microtus to apply all patches

## Adding Patches

Adding new patches to the project is very simple and doesn't require any high effort.

Steps to add a new patch:
1. Navigate to the `patched-minestom` directory in the terminal
2. Modify `patched-minestom` with the changes you want to add
3. Type `git add .` into your terminal to add each file to the staging area
4. Type `git commit -m "Your commit message"` to commit the changes
5. Run `./gradlew rebuildPatches` in the main directory from Microtus to convert your commit into a new patch
6. After the task was successful, you can find your new patch in `/minestom-patches` directory

Now you can create a merge request to add your patch to the repository.

> ❗Please add documentation to your patch and provide some unit tests if possible.
> Tests helps us to track if a feature works expected or not

## Modifying Patches

The process to modify an existing patch is a bit more complex.
There are several methods which can be used to modify a patch.

### Method 1 - Fixup commits

When your changes are not to complex, you can use the fixup commits to modify a patch.

This method has the benefit of being able to compile to test your change without
messing with your HEADs.

#### Manual method

This method works by a temporarily reset of your `HEAD` to the desired commit to edit it using `git rebase`.

1. When you have changes, type `git stash` to store them for a later use
   - You can use `git stash pop` to restore the changes at a later point
2. Use the `git rebase -i base` command to rebase your branch with a specific target
   - When your editor doesn't have a "menu" at the bottom, you are using the `vim` editor.
   - If you don't know how to use `vim`, and don't have the indention to learn it, enter `:q!` and press enter
   - Then type `export EDITOR=nano` and press enter
   - This will change the editor to `nano` which is easier to use
3. Replace the `pick` with ``edit for the commit or patch that you want to modify and save the changes
   - To avoid issues please do this only for **one** commit at the time
4. Add / Make the changes you want to make for the patch
5. Type `git add .` or `git add <file>` to add the changes to the staging area
6. Type `git commit --amend` to commit
   - ❗***Make sure to add `--amend`*** to the command otherwise a new patch will be created
7. Type `git rebase --continue` to continue the rebase process
8. Navigate to the root directory from Microtus
9. Type `./gradlew rebuildPatches` to rebuild the patches
10. Now you can create a merge request to add your modified patches back to this repository

### Automatic method

1. Make your changes at HEAD;
2. Navigation to `patched-minestom` directory in the terminal
3. Type `git add .` or `git add <file>` to add the changes to the staging area
4. A commit message is not required for this because it is only  a temporary commit
5. Type `git commit -a --fixup <commitHash>`
   - Replace `<commitHash>` with the hash of the commit you want to modify
   - You can find the required hash when you type `git log` into your terminal
6. Rebase with the autosquash option:
   - `git rebase -i --autosquash <base>`
7. Go back to the root project folder
8. Type `./gradlew rebuildPatches` to rebuild the patches
9. After the task was successful, you see that the patch was modified and contains now your changes
