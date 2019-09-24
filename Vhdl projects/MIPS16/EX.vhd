----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/09/2019 08:06:40 PM
-- Design Name: 
-- Module Name: EX - Behavioral
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

entity EX is
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
end EX;

architecture Behavioral of EX is
signal rd2_aux, slt_aux,sll_aux,srl_aux:std_logic_vector(15 downto 0):=(others=>'0');
signal AluControl:std_logic_vector(3 downto 0):=(others=>'0');
begin


AluControl<="0001" when (AluOp="000" and func="000") or AluOp="001" else --add
            "0010" when (AluOp="000" and func="001") or AluOp="100" else --sub
            "0011" when (AluOp="000" and func="010") else                --sll
            "0100" when (AluOp="000" and func="011") else                --srl
            "0101" when (AluOp="000" and func="100") or AluOp="101" else --and
            "0110" when (AluOp="000" and func="101") or AluOp="110" else --or
            "0111" when (AluOp="000" and func="110") else                --xor
            "1000" when (AluOp="000" and func="111") else                --slt
            "0000";

rd2_aux<=rd2 when AluSrc='0' else Ext_imm; --mux AluSrc

slt_aux<=x"0001" when rd1<rd2 else x"0000"; --mux slt

sll_aux<=rd1(14 downto 0)& '0' when sa='1' else rd1;

srl_aux<='0' & rd1(15 downto 1) when sa='1' else rd1;           
            
with AluControl select
       AluRes<=rd1+rd2_aux when "0001",
                rd1-rd2_aux when "0010",
                sll_aux when "0011",
                srl_aux when "0100",
                rd1 and rd2_aux when "0101",
                rd1 or rd2_aux when "0110",
                rd1 xor rd2_aux when "0111",
                slt_aux when "1000",
                x"FFFF" when others; 

zero<='1' when rd1=rd2 else '0';         
BranchAddr<=pc+1+Ext_imm;

end Behavioral;
