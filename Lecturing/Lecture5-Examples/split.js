let string = `kjfd;dfjkgb;3845
suidfhas;124gfds;1fgds
jdhfoisd;123123;214`;

string = string.split('\n');

for(let i = 0; i < string.length; i++) {
  string[i] = string[i].split(";");
}

console.table(string)