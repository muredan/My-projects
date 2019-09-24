----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/27/2019 10:07:21 AM
-- Design Name: 
-- Module Name: main - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;


-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity main is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0));
end main;

architecture Behavioral of main is
signal enable,enable2,reset,sa,enable_RegWr,zero,MemWrite_aux:std_logic:='0';
signal pc ,instr,do,rd1,rd2,wd,ext_im,AluRes,BranchAddr,MemData,AluRes_out,jumpAddr_aux,wa:std_logic_vector(15 downto 0):=(others=>'0');
signal control: std_logic_vector(10 downto 0);
signal Jump,RegDst,RegWr,AluSrc,PcSrc,MemRd,MemWr,MemToReg,PCsrc_aux: std_logic:='0';
signal AluOp,func,wa_out:  std_logic_vector(2 downto 0):=(others=>'0');

signal regIF_ID: std_logic_vector(31 downto 0):=(others=>'0');
signal regID_EX: std_logic_vector(78 downto 0):=(others=>'0');
signal regEX_MEM: std_logic_vector(55 downto 0):=(others=>'0');
signal regMEM_WB:std_logic_vector(36 downto 0):=(others=>'0');

component InstF is
 port(clk: in std_logic;
      jumpAddr: in std_logic_vector(15 downto 0);
      branchAddr: in std_logic_vector(15 downto 0);
      PCsrc: in std_logic;
      jump: in std_logic;
      enable:in std_logic;
      reset: in std_logic;
      instr: out std_logic_vector(15 downto 0);
      pc_out:out std_logic_vector(15 downto 0));
end component;

component ID is
port(
clk: in std_logic;
instruction: in std_logic_vector(15 downto 0) ;
RegDst: in std_logic;
RegWr: in std_logic;
wd: in std_logic_vector (15 downto 0);
wa: in std_logic_vector(2 downto 0);
rd1: out std_logic_vector (15 downto 0);
rd2: out std_logic_vector (15 downto 0);
ext_im: out std_logic_vector(15 downto 0);
wa_out: out std_logic_vector(2 downto 0);
sa: out std_logic;
func: out std_logic_vector(2 downto 0)
);
end component;
component EX is
    Port ( rd1 : in STD_LOGIC_VECTOR (15 downto 0);
           rd2 : in STD_LOGIC_VECTOR (15 downto 0);
           AluSrc : in STD_LOGIC;
           Ext_Imm : in STD_LOGIC_VECTOR (15 downto 0);
           sa : in STD_LOGIC;
           func : in STD_LOGIC_VECTOR (2 downto 0);
           AluOp : in STD_LOGIC_VECTOR (2 downto 0);
           pc : in STD_LOGIC_VECTOR (15 downto 0);
           zero : out STD_LOGIC;
           AluRes : out STD_LOGIC_VECTOR (15 downto 0);
           BranchAddr : out STD_LOGIC_VECTOR (15 downto 0));
end component;

component MEM is
port(clk: in std_logic;
       MemWrite:in std_logic;
       MemRead:in std_logic;
       AluRes:in std_logic_vector(15 downto 0);
       rd2: in std_logic_vector(15 downto 0);
       MemData:out std_logic_vector(15 downto 0);
       AluRes_out: out std_logic_vector(15 downto 0)
       );
end component;


     
 component MPG is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC;
           enable : out STD_LOGIC);
end component;
component SSD 
    Port ( clk : in STD_LOGIC;
           digits : in STD_LOGIC_VECTOR (15 downto 0);
           cat: out std_logic_vector(6 downto 0);
           an: out std_logic_vector(3 downto 0));
end component;
begin
 P1: InstF port map(clk=>clk,
      jumpAddr =>jumpAddr_aux,
      branchAddr=> regEX_MEM(51 downto 36),
      PCsrc=>PCsrc_aux,
      jump=>jump,
      enable=>enable,
      reset=>reset,
      instr=>instr,
      pc_out=>pc);

with  regIf_ID(15 downto 13) SELect
    control<="01100000000" when "000", --R tip
             "00110000001" when "001", -- addi
             "00110101001" when "010", --lw
             "00010010001" when "011", --sw
             "00001000100" when "100", --beq
             "00110000101" when "101", --andi
             "00110000110" when "110", --ori
             "10000000111" when "111", --j
             "00000000000" when others;
             
Jump<=control(10);
RegDst<=control(9);
RegWr<=control(8);
AluSrc<=control(7);
PcSrc<=control(6);
MemRd<='1';
MemWr<=control(4);
MemToReg<=control(3);
AluOp<=control(2 downto 0); 
led(10 downto 0)<=control;

P5: ID port map(
        clk=>clk,
        instruction=>RegIF_ID(15 downto 0),
        RegDst=>RegDst,
        RegWr=>enable_RegWr,
        wd=>wd,
        wa=> regMEM_WB(2 downto 0),
        rd1=>rd1,
        rd2=>rd2,
        ext_im=>ext_im,
        wa_out=>wa_out,
        sa=>sa,
        func=>func);
        
--wd<=rd1+rd2;            
enable_RegWr<=regMEM_WB(35) and enable;


P6:EX port map ( rd1=> regID_EX(53 downto 38),
           rd2=> regID_EX(37 downto 22),
           AluSrc=>regID_EX(71),
           Ext_Imm=> regID_EX(21 downto 6),
           sa=> regID_EX(70),
           func=> regID_EX(5 downto 3),
           AluOp=>regID_EX(74 downto 72),
           pc=> regID_EX(69 downto 54),
           zero=>zero,
           AluRes=>AluRes,
           BranchAddr=>BranchAddr);

MemWrite_aux<=enable and regEX_MEM(53);

P7: MEM port map(clk=>clk,
       MemWrite=>MemWrite_aux,
       MemRead=>MemRd,
       AluRes=>regEX_MEM(34 downto 19),
       rd2=> regEX_MEM(18 downto 3),
       MemData=>MemData,
       AluRes_out=>AluRes_out
       );

wd<=regMEM_WB(34 downto 19) when regMEM_WB(36)='1' else regMEM_WB(18 downto 3);
PCsrc_aux<=regEX_MEM(52) and regEX_MEM(35);
jumpAddr_aux<=regIF_ID(31 downto 29) & regIF_ID(12 downto 0);

with sw(2 downto 0) select
        do<=instr when "000",
            pc when "001",
            --regID_EX(53 downto 38) when "010",
           rd1 when "010",
           -- regID_EX(37 downto 22)  when "011",
            rd2 when "011",
            -- regID_EX(21 downto 6) when "100",
            ext_im when "100",
            AluRes when "101",
            MemData when "110",
            wd when others;
            
process(clk)
begin
if rising_edge(clk)then 
    if(enable='1') then
        --------------------
        regIF_ID(31 downto 16)<=pc;
        regIf_ID(15 downto 0)<=instr;
        --------------------
        regID_EX(78)<=MemToReg;
        regID_EX(77)<=RegWr;
        regID_EX(76)<=MemWr;
        regID_EX(75)<=PcSrc;
        regID_EX(74 downto 72)<=AluOp;
        regID_EX(71)<=ALUsrc;
        regID_EX(70)<=sa;
        regID_EX(69 downto 54)<=regIF_ID(31 downto 16);    
        regID_EX(53 downto 38)<=rd1;
        regID_EX(37 downto 22)<=rd2; 
        regID_EX(21 downto 6)<= ext_im;
        regID_EX(5 downto 3)<=func;       
        regID_EX(2 downto 0)<= wa_out; 
        --------------------
        regEX_MEM(55 downto 52)<=regID_EX(78 downto 75);
        regEX_MEM(51 downto 36)<=BranchAddr;
        regEX_MEM(35)<=zero;
        regEX_MEM(34 downto 19)<=AluRes;
        regEX_MEM(18 downto 3)<=regID_EX(37 downto 22);
        regEX_MEM(2 downto 0)<= regID_EX(2 downto 0);
        --------------------
        regMEM_WB(36 downto 35)<= regEX_MEM(55 downto 54);
        regMEM_WB(34 downto 19)<=MemData;
        regMEM_WB(18 downto 3)<= AluRes_out;
        regMEM_WB(2 downto 0)<=  regEX_MEM(2 downto 0);
        --------------------
    end if;
end if;
end process;
--led(13)<=regID_EX(71);
P2: MPG port map (clk,btn(0),enable); 
P3: MPG port map (clk,btn(1),reset);    
P4:SSD port map(clk,do,cat,an);
end Behavioral;
