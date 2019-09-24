----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/27/2019 10:09:42 AM
-- Design Name: 
-- Module Name: IF - Behavioral
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

entity InstF is
 port(clk: in std_logic;
      jumpAddr: in std_logic_vector(15 downto 0);
      branchAddr: in std_logic_vector(15 downto 0);
      PCsrc: in std_logic;
      jump: in std_logic;
      enable:in std_logic;
      reset: in std_logic;
      instr: out std_logic_vector(15 downto 0);
      pc_out:out std_logic_vector(15 downto 0));
end InstF;

architecture Behavioral of InstF is
type ROM_array is array (0 to 255) of std_logic_vector(15 downto 0);
signal ROM:ROM_array:=(
B"001_000_001_0001010",  --0 addi $a0,$zero,10
B"011_101_010_0000000",  --1 sw $a1,0($sp)
x"0000",                 --2 NoOP
x"0000",                 --3 NoOP
B"001_010_010_0000001",  --4 addi $a1,$a1,1
x"0000",                 --5 NoOP
x"0000",                 --6 NoOP
B"011_101_010_0000001",  --7 sw $a1,1($sp)
B"001_000_101_0000010",  --8 add $sp,$zero,2
x"0000",                 --9 NoOP
x"0000",                 --10 NoOP
B"100_001_101_0001101",  --11 beq $sp,$a0,13
x"0000",                 --12 NoOP
x"0000",                 --13 NoOP
x"0000",                 --14 NoOP
B"010_101_010_1111111",  --15 lw $a1,-1($sp)
B"010_101_100_1111110",  --16 lw $a3, -2($sp)
x"0000",                 --17 NoOP
x"0000",                 --18 NoOP
B"000_010_100_100_0_000",--19 add $a3,$a1,$a3
x"0000",                 --20 NoOP
x"0000",                 --21 NoOP
B"011_101_100_0000000",  --22 sw $a3,0($sp)
B"001_101_101_0000001",  --23 addi $sp,$sp,1
B"111_0000000001011",    --24 j 11
others=>x"0000");
signal PC,do: std_logic_vector(15 downto 0);
signal PC_aux:std_logic_vector(15 downto 0):=(others=>'0');
begin

PC_aux<=PC+1 when jump='0' and PCsrc='0' else
        jumpAddr when jump='1'  else
        branchAddr; 


 PC_next:process(clk)
 begin
 if reset='1' then
    PC<=x"0000";
    elsif rising_edge(clk) then
    if enable='1' then
            PC<=PC_aux;
         end if;
         end if;
 end process;
 
 instr<=ROM(conv_integer(PC));
 pc_out<=pc;


end Behavioral;
