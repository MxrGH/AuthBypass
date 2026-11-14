# AuthBypass

Client-side NeoForge helper for Minecraft **26.2** and **NeoForge 26.2.0.88**.

Detects common AuthMeReloaded login/register prompts and can submit the normal
`/login <password>` or `/register <password> <password>` command using a password
you explicitly configure. It does **not** bypass server authentication, forge an
authenticated state, or grant permissions.

## Commands
- `/authbypass password <password>` — save your password locally.
- `/authbypass status` — show configuration status.
- `/authbypass retry` — re-arm prompt detection.
- `/authbypass clear` — clear the saved password.
- `/authbypass mode AUTO` or `LOGIN` — set the mode.

## Build
Requires JDK 25. Run `gradle build` from the project root. The JAR is written
to `build/libs/`.

## Password storage warning
The password is AES-GCM encrypted, but its key is derived from local OS identity
values on the same machine. This is **not** an OS credential vault. Do not reuse
an important password. Remove it with `/authbypass clear` when no longer needed.

## Limitations
Only matching chat messages are detected; custom server wording may need regex
adjustments in `config/authmebypass.json`. Captcha and extra verification steps
must be completed manually.


Build workflow: GitHub Actions builds the mod and uploads the JAR as a workflow artifact.
