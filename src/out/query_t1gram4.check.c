#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Phrase,Year,MatchCount,VolumeCount,VolumeCount1");
  int32_t x2 = 0;
  int32_t x3 = 0;
  int32_t* x4 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x6=0; x6 < 256; x6++) {
    x4[x6] = -1;
  }
  char** x10 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x11 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x12 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x13 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x14 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x15 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x16 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x17 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x18 = 0;
  int32_t x19 = 0;
  int32_t* x20 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x21 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x25 = 0;
  int32_t x22 = open(x0,0);
  int32_t x23 = fsize(x22);
  char* x24 = mmap(0, x23, PROT_READ, MAP_FILE | MAP_SHARED, x22, 0);
  int32_t x87 = (int32_t)0L;
  int32_t x88 = x87 & 255;
  bool x94 = !true;
  for (;;) {
    int32_t x26 = x25;
    bool x27 = x26 < x23;
    if (!x27) break;
    int32_t x29 = x25;
    for (;;) {
      int32_t x30 = x25;
      char x31 = x24[x30];
      bool x32 = x31 != '\t';
      if (!x32) break;
      x25 += 1;
    }
    int32_t x37 = x25;
    x25 += 1;
    int32_t x41 = x25;
    for (;;) {
      int32_t x42 = x25;
      char x43 = x24[x42];
      bool x44 = x43 != '\t';
      if (!x44) break;
      x25 += 1;
    }
    int32_t x49 = x25;
    x25 += 1;
    int32_t x53 = x25;
    for (;;) {
      int32_t x54 = x25;
      char x55 = x24[x54];
      bool x56 = x55 != '\t';
      if (!x56) break;
      x25 += 1;
    }
    int32_t x61 = x25;
    x25 += 1;
    int32_t x65 = x25;
    for (;;) {
      int32_t x66 = x25;
      char x67 = x24[x66];
      bool x68 = x67 != '\n';
      if (!x68) break;
      x25 += 1;
    }
    int32_t x73 = x25;
    x25 += 1;
    int32_t x77 = x19;
    char* x40 = x24+x29;
    x10[x77] = x40;
    int32_t x38 = x37 - x29;
    x11[x77] = x38;
    char* x52 = x24+x41;
    x12[x77] = x52;
    int32_t x50 = x49 - x41;
    x13[x77] = x50;
    char* x64 = x24+x53;
    x14[x77] = x64;
    int32_t x62 = x61 - x53;
    x15[x77] = x62;
    char* x76 = x24+x65;
    x16[x77] = x76;
    int32_t x74 = x73 - x65;
    x17[x77] = x74;
    x19 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x89 = x88;
    for (;;) {
      int32_t x90 = x89;
      int32_t x91 = x4[x90];
      bool x93 = x91 == -1;
      bool x95;
      if (x93) {
        x95 = false;
      } else {
        x95 = x94;
      }
      if (!x95) break;
      int32_t x97 = x89;
      int32_t x98 = x97 + 1;
      int32_t x99 = x98 & 255;
      x89 = x99;
    }
    int32_t x103 = x89;
    int32_t x104 = x4[x103];
    bool x105 = x104 == -1;
    int32_t x111;
    if (x105) {
      int32_t x106 = x3;
      x3 += 1;
      x4[x103] = x106;
      x21[x106] = 0;
      x111 = x106;
    } else {
      x111 = x104;
    }
    int32_t x113 = x111;
    //#hash_lookup
    int32_t x114 = x21[x113];
    int32_t x115 = x113 * 256;
    int32_t x116 = x115 + x114;
    x20[x116] = x77;
    int32_t x118 = x114 + 1;
    x21[x113] = x118;
  }
  int32_t x126 = 0;
  int32_t x123 = open("src/data/words.csv",0);
  int32_t x124 = fsize(x123);
  char* x125 = mmap(0, x124, PROT_READ, MAP_FILE | MAP_SHARED, x123, 0);
  for (;;) {
    int32_t x127 = x126;
    char x128 = x125[x127];
    bool x129 = x128 != ',';
    if (!x129) break;
    x126 += 1;
  }
  int32_t x134 = x126;
  x126 += 1;
  int32_t x137 = x126;
  for (;;) {
    int32_t x138 = x126;
    char x139 = x125[x138];
    bool x140 = x139 != '\n';
    if (!x140) break;
    x126 += 1;
  }
  int32_t x145 = x126;
  x126 += 1;
  for (;;) {
    int32_t x149 = x126;
    bool x150 = x149 < x124;
    if (!x150) break;
    int32_t x152 = x126;
    for (;;) {
      int32_t x153 = x126;
      char x154 = x125[x153];
      bool x155 = x154 != ',';
      if (!x155) break;
      x126 += 1;
    }
    int32_t x160 = x126;
    x126 += 1;
    int32_t x164 = x126;
    for (;;) {
      int32_t x165 = x126;
      char x166 = x125[x165];
      bool x167 = x166 != '\n';
      if (!x167) break;
      x126 += 1;
    }
    int32_t x172 = x126;
    x126 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x176 = x88;
    for (;;) {
      int32_t x177 = x176;
      int32_t x178 = x4[x177];
      bool x180 = x178 == -1;
      bool x181;
      if (x180) {
        x181 = false;
      } else {
        x181 = x94;
      }
      if (!x181) break;
      int32_t x183 = x176;
      int32_t x184 = x183 + 1;
      int32_t x185 = x184 & 255;
      x176 = x185;
    }
    int32_t x189 = x176;
    int32_t x190 = x4[x189];
    int32_t x192 = x190;
    //#hash_lookup
    int32_t x193 = x21[x192];
    int32_t x194 = x192 * 256;
    int32_t x195 = x194 + x193;
    char* x175 = x125+x164;
    for(int x197=x194; x197 < x195; x197++) {
      int32_t x198 = x20[x197];
      char* x199 = x10[x198];
      int32_t x200 = x11[x198];
      char* x201 = x12[x198];
      int32_t x202 = x13[x198];
      char* x203 = x14[x198];
      int32_t x204 = x15[x198];
      char* x205 = x16[x198];
      int32_t x206 = x17[x198];
      int32_t x207 = printll(x199);
      printf(",");
      int32_t x209 = printll(x201);
      printf(",");
      int32_t x211 = printll(x203);
      printf(",");
      int32_t x213 = printll(x205);
      printf(",");
      int32_t x215 = printll(x175);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
