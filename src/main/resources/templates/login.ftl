<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="tachyons.min.css">
</head>
<body>
<main class="pa4 black-80">
    <form class="measure center" action="/login" method="post">
        <fieldset id="sign_up" class="ba b--transparent ph0 mh0">
            <legend class="f4 fw6 ph0 mh0">Sign In</legend>
            <div class="mt3">
                <label class="db fw6 lh-copy f6" for="email-address">User Name</label>
                <input class="pa2 input-reset ba bg-transparent hover-bg-black hover-white w-100" type="text" name="username" required>
            </div>
            <div class="mv3">
                <label class="db fw6 lh-copy f6" for="password">Password</label>
                <input class="b pa2 input-reset ba bg-transparent hover-bg-black hover-white w-100" type="password" name="password" required>
            </div>
        </fieldset>
        <div class="">
            <input class="b ph3 pv2 input-reset ba b--black bg-transparent grow pointer f6 dib" type="submit" value="Sign in">
        </div>
    </form>
</main>
</body>
</html>
