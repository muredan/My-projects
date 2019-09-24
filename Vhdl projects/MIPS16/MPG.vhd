----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/01/2019 04:30:29 PM
-- Design Name: 
-- Module Name: MPG - Behavioral
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
use Ieee.std_logic_unsigned.all;
use ieee.std_logic_arith.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity MPG is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC;
           enable : out STD_LOGIC);
end MPG;

architecture Behavioral of MPG is
signal sig: std_logic_vector(15 downto 0):= (others=>'0');
signal Q1,Q2,Q3,en: std_logic :='0';
begin
process(clk)
begin
    if rising_edge(clk) then
    sig<=sig+1;
    end if;
end process;

en<='1' when sig = x"ffff" else '0';

process (clk)
begin
if rising_edge(clk) then 
    if en = '1' then
         Q1<=btn;
    end if;
        Q2<=Q1;
        Q3<=Q2;
end if;
end process;

enable<=Q2 and not Q3;
end Behavioral;
