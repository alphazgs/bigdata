def decimals(n: Long): Int=
  if (n == 1) 0 else 1 + decimals(n / 10)


//decimals(406)

45/10