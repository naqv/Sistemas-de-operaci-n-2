# 
#David Lilue	--- 09-10444
#Veronica Liñayo --- 08-10615
# 
#Grupo 33


# Compilers
JAVAC = javac
RMIC = rmic

# Sources
AUTHEN = a_rmifs.java
SERVER = s_rmifs.java
CLIENT = c_rmifs.java
GNU = gnu
GETOPT = getopt

all: getopt todo
	$(RMIC) RmiAuthenImpl RmiServerImpl

todo: $(AUTHEN) $(SERVER) $(CLIENT)
	$(JAVAC) $^

getopt:
	cd $(GNU); make -C $(GETOPT)

clean: cleangnu
	\rm -f *.class *.*~

cleangnu:
	cd $(GNU); make -C $(GETOPT) clean
