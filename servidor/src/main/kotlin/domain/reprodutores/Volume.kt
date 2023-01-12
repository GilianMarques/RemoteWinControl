package domain.reprodutores


object Volume {
    fun aumentar() {
        Cmd.run("nircmd.exe sendkey 175 down")
        Cmd.run("nircmd.exe sendkey 175 up")

    }

    fun diminuir() {
        Cmd.run("nircmd.exe sendkey 174 down")
        Cmd.run("nircmd.exe sendkey 174 up")
    }


}