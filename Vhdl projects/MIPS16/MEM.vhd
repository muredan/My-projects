----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/10/2019 10:21:19 AM
-- Design Name: 
-- Module Name: MEM - Behavioral
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

entity MEM is
port(clk: in std_logic;
       MemWrite:in std_logic;
       MemRead:in std_logic;
       AluRes:in std_logic_vector(15 downto 0);
       rd2: in std_logic_vector(15 downto 0);
       MemData:out std_logic_vector(15 downto 0);
       AluRes_out: out std_logic_vector(15 downto 0)
       );
end MEM;

architecture Behavioral of MEM is
    type ram_array is array(0 to 255) of std_logic_vector(15 downto 0);
    signal RAM:ram_array:=(others=>x"0000");
    signal AluRes_aux: std_logic_vector(15 downto 0):=(others=>'0');
begin
AluRes_aux<=AluRes;
process(clk,MemRead)
begin        
if rising_edge(clk) then
        if MemWrite='1' then
            RAM(conv_integer(AluRes(7 downto 0)))<=rd2;
        end if;
end if;
end process;
MemData<= RAM(conv_integer(AluRes(7 downto 0)));
AluRes_out<=AluRes_aux;

end Behavioral;
