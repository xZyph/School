-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 25, 2019 at 09:18 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uinv19gr27`
--
CREATE DATABASE IF NOT EXISTS `uinv19gr27` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `uinv19gr27`;

-- --------------------------------------------------------

--
-- Table structure for table `syst_color`
--

CREATE TABLE `syst_color` (
  `color_id` int(10) NOT NULL,
  `color_name` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `syst_model`
--

CREATE TABLE `syst_model` (
  `model_id` int(10) NOT NULL,
  `model_name` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `syst_products`
--

CREATE TABLE `syst_products` (
  `product_id` int(10) NOT NULL,
  `product_brand` varchar(50) NOT NULL,
  `product_title` varchar(50) NOT NULL,
  `product_desc` varchar(500) NOT NULL,
  `product_price` int(10) NOT NULL,
  `product_img` varchar(50) NOT NULL,
  `product_status` enum('0','1') NOT NULL DEFAULT '0',
  `type_id` int(10) NOT NULL,
  `product_rating` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `syst_products`
--

INSERT INTO `syst_products` (`product_id`, `product_brand`, `product_title`, `product_desc`, `product_price`, `product_img`, `product_status`, `type_id`, `product_rating`) VALUES
(1, 'Hak5', 'WIFI Pineapple', 'The WiFi Pineapple® NANO and TETRA are the 6th generation pentest platforms from Hak5. Thoughtfully developed for mobile and persistent deployments, they build on over 10 years of WiFi attack expertise.', 200, 'pineapple.jpg', '1', 1, 5),
(2, 'Great Scott Gadgets', 'Ubertooth One', 'The Ubertooth One is an open source 2.4 GHz wireless development platform suitable for Bluetooth experimentation. Commercial Bluetooth monitoring equipment can easily be priced at over $10,000 , so the Ubertooth was designed to be an affordable alternative platform for monitoring and development of new BT, BLE, similar and wireless technologies.', 150, 'ubertooth.jpg', '1', 1, 5),
(3, 'Inverse Path', 'USB Armory Bundle', 'The USB armory is a discreet, flash-drive sized computer that’s secure, open source, and ideal for developing and running a range of applications. Developers and ordinary users alike benefit from this device’s quick NXP i.MX53 processor, advanced security features like secure boot and ARM® TrustZone® integration, fully customizable operating environment, exceptional native support and much more.', 200, 'usb-armory.jpg', '1', 2, 6),
(4, 'Hak5', 'Hak5 Essentials Field Kit', 'This exclusive 10-piece Field Kit combines the essential Hak5 tools compiled in our signature soft cover equipment organizer.\r\n\r\nIncludes:\r\n   * WiFi Pineapple NANO\r\n   * USB Rubber Ducky\r\n   * LAN Turtle SD\r\n   * The Hak5 Field Guides for all related devices\r\n   * All accompanying accessories, adapters, cables and card readers\r\n', 250, 'hak5field.png', '1', 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `syst_product_has_variants`
--

CREATE TABLE `syst_product_has_variants` (
  `product_id` int(10) NOT NULL,
  `model_id` int(10) NOT NULL,
  `color_id` int(10) NOT NULL,
  `product_amount` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `syst_product_type`
--

CREATE TABLE `syst_product_type` (
  `type_id` int(10) NOT NULL,
  `type_title` varchar(50) NOT NULL,
  `type_desc` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `syst_product_type`
--

INSERT INTO `syst_product_type` (`type_id`, `type_title`, `type_desc`) VALUES
(1, 'WIFI Tools', 'The leading rogue access point and WiFi pentest toolkit for close access operations. Passive and active attacks analyze vulnerable and misconfigured devices.'),
(2, 'USB Attack Tools', 'The best penetration testers know that with the right tools and a few seconds of physical access, all bets are off. Since 2005 Hak5 has been developing just such tools – combining lethal power, elegance and simplicity'),
(3, 'Network Implants', 'Simple and effective. These stealthy Ethernet multi-tools are expandable platforms for remote access and man-in-the-middle. Their discreet nature allows them to easily blend into network environments.'),
(4, 'HAK5  Field Kits', 'Field proven gear assembled for your specific objective.');

-- --------------------------------------------------------

--
-- Table structure for table `uin_commentmeta`
--

CREATE TABLE `uin_commentmeta` (
  `meta_id` bigint(20) UNSIGNED NOT NULL,
  `comment_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_value` longtext COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `uin_comments`
--

CREATE TABLE `uin_comments` (
  `comment_ID` bigint(20) UNSIGNED NOT NULL,
  `comment_post_ID` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `comment_author` tinytext COLLATE utf8mb4_unicode_ci NOT NULL,
  `comment_author_email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_author_url` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_author_IP` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `comment_date_gmt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `comment_content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `comment_karma` int(11) NOT NULL DEFAULT '0',
  `comment_approved` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1',
  `comment_agent` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_parent` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `user_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_comments`
--

INSERT INTO `uin_comments` (`comment_ID`, `comment_post_ID`, `comment_author`, `comment_author_email`, `comment_author_url`, `comment_author_IP`, `comment_date`, `comment_date_gmt`, `comment_content`, `comment_karma`, `comment_approved`, `comment_agent`, `comment_type`, `comment_parent`, `user_id`) VALUES
(1, 1, 'A WordPress Commenter', 'wapuu@wordpress.example', 'https://wordpress.org/', '', '2019-04-11 09:04:48', '2019-04-11 09:04:48', 'Hi, this is a comment.\nTo get started with moderating, editing, and deleting comments, please visit the Comments screen in the dashboard.\nCommenter avatars come from <a href=\"https://gravatar.com\">Gravatar</a>.', 0, 'post-trashed', '', '', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `uin_links`
--

CREATE TABLE `uin_links` (
  `link_id` bigint(20) UNSIGNED NOT NULL,
  `link_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_image` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_target` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_visible` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y',
  `link_owner` bigint(20) UNSIGNED NOT NULL DEFAULT '1',
  `link_rating` int(11) NOT NULL DEFAULT '0',
  `link_updated` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `link_rel` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `link_notes` mediumtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `link_rss` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `uin_options`
--

CREATE TABLE `uin_options` (
  `option_id` bigint(20) UNSIGNED NOT NULL,
  `option_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `option_value` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `autoload` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'yes'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_options`
--

INSERT INTO `uin_options` (`option_id`, `option_name`, `option_value`, `autoload`) VALUES
(1, 'siteurl', 'https://uin.test/', 'yes'),
(2, 'home', 'https://uin.test/', 'yes'),
(3, 'blogname', 'PlasticOverflow', 'yes'),
(4, 'blogdescription', 'Your source of hackware - By Team NullPointerException', 'yes'),
(5, 'users_can_register', '0', 'yes'),
(6, 'admin_email', 'marius.mikelsen@hiof.no', 'yes'),
(7, 'start_of_week', '1', 'yes'),
(8, 'use_balanceTags', '0', 'yes'),
(9, 'use_smilies', '1', 'yes'),
(10, 'require_name_email', '1', 'yes'),
(11, 'comments_notify', '1', 'yes'),
(12, 'posts_per_rss', '10', 'yes'),
(13, 'rss_use_excerpt', '0', 'yes'),
(14, 'mailserver_url', 'mail.example.com', 'yes'),
(15, 'mailserver_login', 'login@example.com', 'yes'),
(16, 'mailserver_pass', 'password', 'yes'),
(17, 'mailserver_port', '110', 'yes'),
(18, 'default_category', '1', 'yes'),
(19, 'default_comment_status', 'open', 'yes'),
(20, 'default_ping_status', 'open', 'yes'),
(21, 'default_pingback_flag', '1', 'yes'),
(22, 'posts_per_page', '10', 'yes'),
(23, 'date_format', 'j. F Y', 'yes'),
(24, 'time_format', 'H:i', 'yes'),
(25, 'links_updated_date_format', 'F j, Y g:i a', 'yes'),
(26, 'comment_moderation', '0', 'yes'),
(27, 'moderation_notify', '1', 'yes'),
(28, 'permalink_structure', '/%postname%/', 'yes'),
(29, 'rewrite_rules', 'a:87:{s:11:\"^wp-json/?$\";s:22:\"index.php?rest_route=/\";s:14:\"^wp-json/(.*)?\";s:33:\"index.php?rest_route=/$matches[1]\";s:21:\"^index.php/wp-json/?$\";s:22:\"index.php?rest_route=/\";s:24:\"^index.php/wp-json/(.*)?\";s:33:\"index.php?rest_route=/$matches[1]\";s:47:\"category/(.+?)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:52:\"index.php?category_name=$matches[1]&feed=$matches[2]\";s:42:\"category/(.+?)/(feed|rdf|rss|rss2|atom)/?$\";s:52:\"index.php?category_name=$matches[1]&feed=$matches[2]\";s:23:\"category/(.+?)/embed/?$\";s:46:\"index.php?category_name=$matches[1]&embed=true\";s:35:\"category/(.+?)/page/?([0-9]{1,})/?$\";s:53:\"index.php?category_name=$matches[1]&paged=$matches[2]\";s:17:\"category/(.+?)/?$\";s:35:\"index.php?category_name=$matches[1]\";s:44:\"tag/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:42:\"index.php?tag=$matches[1]&feed=$matches[2]\";s:39:\"tag/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:42:\"index.php?tag=$matches[1]&feed=$matches[2]\";s:20:\"tag/([^/]+)/embed/?$\";s:36:\"index.php?tag=$matches[1]&embed=true\";s:32:\"tag/([^/]+)/page/?([0-9]{1,})/?$\";s:43:\"index.php?tag=$matches[1]&paged=$matches[2]\";s:14:\"tag/([^/]+)/?$\";s:25:\"index.php?tag=$matches[1]\";s:45:\"type/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:50:\"index.php?post_format=$matches[1]&feed=$matches[2]\";s:40:\"type/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:50:\"index.php?post_format=$matches[1]&feed=$matches[2]\";s:21:\"type/([^/]+)/embed/?$\";s:44:\"index.php?post_format=$matches[1]&embed=true\";s:33:\"type/([^/]+)/page/?([0-9]{1,})/?$\";s:51:\"index.php?post_format=$matches[1]&paged=$matches[2]\";s:15:\"type/([^/]+)/?$\";s:33:\"index.php?post_format=$matches[1]\";s:12:\"robots\\.txt$\";s:18:\"index.php?robots=1\";s:48:\".*wp-(atom|rdf|rss|rss2|feed|commentsrss2)\\.php$\";s:18:\"index.php?feed=old\";s:20:\".*wp-app\\.php(/.*)?$\";s:19:\"index.php?error=403\";s:18:\".*wp-register.php$\";s:23:\"index.php?register=true\";s:32:\"feed/(feed|rdf|rss|rss2|atom)/?$\";s:27:\"index.php?&feed=$matches[1]\";s:27:\"(feed|rdf|rss|rss2|atom)/?$\";s:27:\"index.php?&feed=$matches[1]\";s:8:\"embed/?$\";s:21:\"index.php?&embed=true\";s:20:\"page/?([0-9]{1,})/?$\";s:28:\"index.php?&paged=$matches[1]\";s:41:\"comments/feed/(feed|rdf|rss|rss2|atom)/?$\";s:42:\"index.php?&feed=$matches[1]&withcomments=1\";s:36:\"comments/(feed|rdf|rss|rss2|atom)/?$\";s:42:\"index.php?&feed=$matches[1]&withcomments=1\";s:17:\"comments/embed/?$\";s:21:\"index.php?&embed=true\";s:44:\"search/(.+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:40:\"index.php?s=$matches[1]&feed=$matches[2]\";s:39:\"search/(.+)/(feed|rdf|rss|rss2|atom)/?$\";s:40:\"index.php?s=$matches[1]&feed=$matches[2]\";s:20:\"search/(.+)/embed/?$\";s:34:\"index.php?s=$matches[1]&embed=true\";s:32:\"search/(.+)/page/?([0-9]{1,})/?$\";s:41:\"index.php?s=$matches[1]&paged=$matches[2]\";s:14:\"search/(.+)/?$\";s:23:\"index.php?s=$matches[1]\";s:47:\"author/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:50:\"index.php?author_name=$matches[1]&feed=$matches[2]\";s:42:\"author/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:50:\"index.php?author_name=$matches[1]&feed=$matches[2]\";s:23:\"author/([^/]+)/embed/?$\";s:44:\"index.php?author_name=$matches[1]&embed=true\";s:35:\"author/([^/]+)/page/?([0-9]{1,})/?$\";s:51:\"index.php?author_name=$matches[1]&paged=$matches[2]\";s:17:\"author/([^/]+)/?$\";s:33:\"index.php?author_name=$matches[1]\";s:69:\"([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})/feed/(feed|rdf|rss|rss2|atom)/?$\";s:80:\"index.php?year=$matches[1]&monthnum=$matches[2]&day=$matches[3]&feed=$matches[4]\";s:64:\"([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})/(feed|rdf|rss|rss2|atom)/?$\";s:80:\"index.php?year=$matches[1]&monthnum=$matches[2]&day=$matches[3]&feed=$matches[4]\";s:45:\"([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})/embed/?$\";s:74:\"index.php?year=$matches[1]&monthnum=$matches[2]&day=$matches[3]&embed=true\";s:57:\"([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})/page/?([0-9]{1,})/?$\";s:81:\"index.php?year=$matches[1]&monthnum=$matches[2]&day=$matches[3]&paged=$matches[4]\";s:39:\"([0-9]{4})/([0-9]{1,2})/([0-9]{1,2})/?$\";s:63:\"index.php?year=$matches[1]&monthnum=$matches[2]&day=$matches[3]\";s:56:\"([0-9]{4})/([0-9]{1,2})/feed/(feed|rdf|rss|rss2|atom)/?$\";s:64:\"index.php?year=$matches[1]&monthnum=$matches[2]&feed=$matches[3]\";s:51:\"([0-9]{4})/([0-9]{1,2})/(feed|rdf|rss|rss2|atom)/?$\";s:64:\"index.php?year=$matches[1]&monthnum=$matches[2]&feed=$matches[3]\";s:32:\"([0-9]{4})/([0-9]{1,2})/embed/?$\";s:58:\"index.php?year=$matches[1]&monthnum=$matches[2]&embed=true\";s:44:\"([0-9]{4})/([0-9]{1,2})/page/?([0-9]{1,})/?$\";s:65:\"index.php?year=$matches[1]&monthnum=$matches[2]&paged=$matches[3]\";s:26:\"([0-9]{4})/([0-9]{1,2})/?$\";s:47:\"index.php?year=$matches[1]&monthnum=$matches[2]\";s:43:\"([0-9]{4})/feed/(feed|rdf|rss|rss2|atom)/?$\";s:43:\"index.php?year=$matches[1]&feed=$matches[2]\";s:38:\"([0-9]{4})/(feed|rdf|rss|rss2|atom)/?$\";s:43:\"index.php?year=$matches[1]&feed=$matches[2]\";s:19:\"([0-9]{4})/embed/?$\";s:37:\"index.php?year=$matches[1]&embed=true\";s:31:\"([0-9]{4})/page/?([0-9]{1,})/?$\";s:44:\"index.php?year=$matches[1]&paged=$matches[2]\";s:13:\"([0-9]{4})/?$\";s:26:\"index.php?year=$matches[1]\";s:27:\".?.+?/attachment/([^/]+)/?$\";s:32:\"index.php?attachment=$matches[1]\";s:37:\".?.+?/attachment/([^/]+)/trackback/?$\";s:37:\"index.php?attachment=$matches[1]&tb=1\";s:57:\".?.+?/attachment/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:52:\".?.+?/attachment/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:52:\".?.+?/attachment/([^/]+)/comment-page-([0-9]{1,})/?$\";s:50:\"index.php?attachment=$matches[1]&cpage=$matches[2]\";s:33:\".?.+?/attachment/([^/]+)/embed/?$\";s:43:\"index.php?attachment=$matches[1]&embed=true\";s:16:\"(.?.+?)/embed/?$\";s:41:\"index.php?pagename=$matches[1]&embed=true\";s:20:\"(.?.+?)/trackback/?$\";s:35:\"index.php?pagename=$matches[1]&tb=1\";s:40:\"(.?.+?)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:47:\"index.php?pagename=$matches[1]&feed=$matches[2]\";s:35:\"(.?.+?)/(feed|rdf|rss|rss2|atom)/?$\";s:47:\"index.php?pagename=$matches[1]&feed=$matches[2]\";s:28:\"(.?.+?)/page/?([0-9]{1,})/?$\";s:48:\"index.php?pagename=$matches[1]&paged=$matches[2]\";s:35:\"(.?.+?)/comment-page-([0-9]{1,})/?$\";s:48:\"index.php?pagename=$matches[1]&cpage=$matches[2]\";s:24:\"(.?.+?)(?:/([0-9]+))?/?$\";s:47:\"index.php?pagename=$matches[1]&page=$matches[2]\";s:27:\"[^/]+/attachment/([^/]+)/?$\";s:32:\"index.php?attachment=$matches[1]\";s:37:\"[^/]+/attachment/([^/]+)/trackback/?$\";s:37:\"index.php?attachment=$matches[1]&tb=1\";s:57:\"[^/]+/attachment/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:52:\"[^/]+/attachment/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:52:\"[^/]+/attachment/([^/]+)/comment-page-([0-9]{1,})/?$\";s:50:\"index.php?attachment=$matches[1]&cpage=$matches[2]\";s:33:\"[^/]+/attachment/([^/]+)/embed/?$\";s:43:\"index.php?attachment=$matches[1]&embed=true\";s:16:\"([^/]+)/embed/?$\";s:37:\"index.php?name=$matches[1]&embed=true\";s:20:\"([^/]+)/trackback/?$\";s:31:\"index.php?name=$matches[1]&tb=1\";s:40:\"([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:43:\"index.php?name=$matches[1]&feed=$matches[2]\";s:35:\"([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:43:\"index.php?name=$matches[1]&feed=$matches[2]\";s:28:\"([^/]+)/page/?([0-9]{1,})/?$\";s:44:\"index.php?name=$matches[1]&paged=$matches[2]\";s:35:\"([^/]+)/comment-page-([0-9]{1,})/?$\";s:44:\"index.php?name=$matches[1]&cpage=$matches[2]\";s:24:\"([^/]+)(?:/([0-9]+))?/?$\";s:43:\"index.php?name=$matches[1]&page=$matches[2]\";s:16:\"[^/]+/([^/]+)/?$\";s:32:\"index.php?attachment=$matches[1]\";s:26:\"[^/]+/([^/]+)/trackback/?$\";s:37:\"index.php?attachment=$matches[1]&tb=1\";s:46:\"[^/]+/([^/]+)/feed/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:41:\"[^/]+/([^/]+)/(feed|rdf|rss|rss2|atom)/?$\";s:49:\"index.php?attachment=$matches[1]&feed=$matches[2]\";s:41:\"[^/]+/([^/]+)/comment-page-([0-9]{1,})/?$\";s:50:\"index.php?attachment=$matches[1]&cpage=$matches[2]\";s:22:\"[^/]+/([^/]+)/embed/?$\";s:43:\"index.php?attachment=$matches[1]&embed=true\";}', 'yes'),
(30, 'hack_file', '0', 'yes'),
(31, 'blog_charset', 'UTF-8', 'yes'),
(32, 'moderation_keys', '', 'no'),
(33, 'active_plugins', 'a:0:{}', 'yes'),
(34, 'category_base', '', 'yes'),
(35, 'ping_sites', 'http://rpc.pingomatic.com/', 'yes'),
(36, 'comment_max_links', '2', 'yes'),
(37, 'gmt_offset', '0', 'yes'),
(38, 'default_email_category', '1', 'yes'),
(39, 'recently_edited', '', 'no'),
(40, 'template', 'grow', 'yes'),
(41, 'stylesheet', 'plasticoverflow', 'yes'),
(42, 'comment_whitelist', '1', 'yes'),
(43, 'blacklist_keys', '', 'no'),
(44, 'comment_registration', '0', 'yes'),
(45, 'html_type', 'text/html', 'yes'),
(46, 'use_trackback', '0', 'yes'),
(47, 'default_role', 'subscriber', 'yes'),
(48, 'db_version', '44719', 'yes'),
(49, 'uploads_use_yearmonth_folders', '1', 'yes'),
(50, 'upload_path', '', 'yes'),
(51, 'blog_public', '1', 'yes'),
(52, 'default_link_category', '2', 'yes'),
(53, 'show_on_front', 'posts', 'yes'),
(54, 'tag_base', '', 'yes'),
(55, 'show_avatars', '1', 'yes'),
(56, 'avatar_rating', 'G', 'yes'),
(57, 'upload_url_path', '', 'yes'),
(58, 'thumbnail_size_w', '150', 'yes'),
(59, 'thumbnail_size_h', '150', 'yes'),
(60, 'thumbnail_crop', '1', 'yes'),
(61, 'medium_size_w', '300', 'yes'),
(62, 'medium_size_h', '300', 'yes'),
(63, 'avatar_default', 'mystery', 'yes'),
(64, 'large_size_w', '1024', 'yes'),
(65, 'large_size_h', '1024', 'yes'),
(66, 'image_default_link_type', 'none', 'yes'),
(67, 'image_default_size', '', 'yes'),
(68, 'image_default_align', '', 'yes'),
(69, 'close_comments_for_old_posts', '0', 'yes'),
(70, 'close_comments_days_old', '14', 'yes'),
(71, 'thread_comments', '1', 'yes'),
(72, 'thread_comments_depth', '5', 'yes'),
(73, 'page_comments', '0', 'yes'),
(74, 'comments_per_page', '50', 'yes'),
(75, 'default_comments_page', 'newest', 'yes'),
(76, 'comment_order', 'asc', 'yes'),
(77, 'sticky_posts', 'a:0:{}', 'yes'),
(78, 'widget_categories', 'a:2:{i:2;a:4:{s:5:\"title\";s:0:\"\";s:5:\"count\";i:0;s:12:\"hierarchical\";i:0;s:8:\"dropdown\";i:0;}s:12:\"_multiwidget\";i:1;}', 'yes'),
(79, 'widget_text', 'a:2:{i:1;a:0:{}s:12:\"_multiwidget\";i:1;}', 'yes'),
(80, 'widget_rss', 'a:2:{i:1;a:0:{}s:12:\"_multiwidget\";i:1;}', 'yes'),
(81, 'uninstall_plugins', 'a:0:{}', 'no'),
(82, 'timezone_string', '', 'yes'),
(83, 'page_for_posts', '0', 'yes'),
(84, 'page_on_front', '0', 'yes'),
(85, 'default_post_format', '0', 'yes'),
(86, 'link_manager_enabled', '0', 'yes'),
(87, 'finished_splitting_shared_terms', '1', 'yes'),
(88, 'site_icon', '0', 'yes'),
(89, 'medium_large_size_w', '768', 'yes'),
(90, 'medium_large_size_h', '0', 'yes'),
(91, 'wp_page_for_privacy_policy', '3', 'yes'),
(92, 'show_comments_cookies_opt_in', '1', 'yes'),
(93, 'initial_db_version', '44719', 'yes'),
(94, 'uin_user_roles', 'a:5:{s:13:\"administrator\";a:2:{s:4:\"name\";s:13:\"Administrator\";s:12:\"capabilities\";a:61:{s:13:\"switch_themes\";b:1;s:11:\"edit_themes\";b:1;s:16:\"activate_plugins\";b:1;s:12:\"edit_plugins\";b:1;s:10:\"edit_users\";b:1;s:10:\"edit_files\";b:1;s:14:\"manage_options\";b:1;s:17:\"moderate_comments\";b:1;s:17:\"manage_categories\";b:1;s:12:\"manage_links\";b:1;s:12:\"upload_files\";b:1;s:6:\"import\";b:1;s:15:\"unfiltered_html\";b:1;s:10:\"edit_posts\";b:1;s:17:\"edit_others_posts\";b:1;s:20:\"edit_published_posts\";b:1;s:13:\"publish_posts\";b:1;s:10:\"edit_pages\";b:1;s:4:\"read\";b:1;s:8:\"level_10\";b:1;s:7:\"level_9\";b:1;s:7:\"level_8\";b:1;s:7:\"level_7\";b:1;s:7:\"level_6\";b:1;s:7:\"level_5\";b:1;s:7:\"level_4\";b:1;s:7:\"level_3\";b:1;s:7:\"level_2\";b:1;s:7:\"level_1\";b:1;s:7:\"level_0\";b:1;s:17:\"edit_others_pages\";b:1;s:20:\"edit_published_pages\";b:1;s:13:\"publish_pages\";b:1;s:12:\"delete_pages\";b:1;s:19:\"delete_others_pages\";b:1;s:22:\"delete_published_pages\";b:1;s:12:\"delete_posts\";b:1;s:19:\"delete_others_posts\";b:1;s:22:\"delete_published_posts\";b:1;s:20:\"delete_private_posts\";b:1;s:18:\"edit_private_posts\";b:1;s:18:\"read_private_posts\";b:1;s:20:\"delete_private_pages\";b:1;s:18:\"edit_private_pages\";b:1;s:18:\"read_private_pages\";b:1;s:12:\"delete_users\";b:1;s:12:\"create_users\";b:1;s:17:\"unfiltered_upload\";b:1;s:14:\"edit_dashboard\";b:1;s:14:\"update_plugins\";b:1;s:14:\"delete_plugins\";b:1;s:15:\"install_plugins\";b:1;s:13:\"update_themes\";b:1;s:14:\"install_themes\";b:1;s:11:\"update_core\";b:1;s:10:\"list_users\";b:1;s:12:\"remove_users\";b:1;s:13:\"promote_users\";b:1;s:18:\"edit_theme_options\";b:1;s:13:\"delete_themes\";b:1;s:6:\"export\";b:1;}}s:6:\"editor\";a:2:{s:4:\"name\";s:6:\"Editor\";s:12:\"capabilities\";a:34:{s:17:\"moderate_comments\";b:1;s:17:\"manage_categories\";b:1;s:12:\"manage_links\";b:1;s:12:\"upload_files\";b:1;s:15:\"unfiltered_html\";b:1;s:10:\"edit_posts\";b:1;s:17:\"edit_others_posts\";b:1;s:20:\"edit_published_posts\";b:1;s:13:\"publish_posts\";b:1;s:10:\"edit_pages\";b:1;s:4:\"read\";b:1;s:7:\"level_7\";b:1;s:7:\"level_6\";b:1;s:7:\"level_5\";b:1;s:7:\"level_4\";b:1;s:7:\"level_3\";b:1;s:7:\"level_2\";b:1;s:7:\"level_1\";b:1;s:7:\"level_0\";b:1;s:17:\"edit_others_pages\";b:1;s:20:\"edit_published_pages\";b:1;s:13:\"publish_pages\";b:1;s:12:\"delete_pages\";b:1;s:19:\"delete_others_pages\";b:1;s:22:\"delete_published_pages\";b:1;s:12:\"delete_posts\";b:1;s:19:\"delete_others_posts\";b:1;s:22:\"delete_published_posts\";b:1;s:20:\"delete_private_posts\";b:1;s:18:\"edit_private_posts\";b:1;s:18:\"read_private_posts\";b:1;s:20:\"delete_private_pages\";b:1;s:18:\"edit_private_pages\";b:1;s:18:\"read_private_pages\";b:1;}}s:6:\"author\";a:2:{s:4:\"name\";s:6:\"Author\";s:12:\"capabilities\";a:10:{s:12:\"upload_files\";b:1;s:10:\"edit_posts\";b:1;s:20:\"edit_published_posts\";b:1;s:13:\"publish_posts\";b:1;s:4:\"read\";b:1;s:7:\"level_2\";b:1;s:7:\"level_1\";b:1;s:7:\"level_0\";b:1;s:12:\"delete_posts\";b:1;s:22:\"delete_published_posts\";b:1;}}s:11:\"contributor\";a:2:{s:4:\"name\";s:11:\"Contributor\";s:12:\"capabilities\";a:5:{s:10:\"edit_posts\";b:1;s:4:\"read\";b:1;s:7:\"level_1\";b:1;s:7:\"level_0\";b:1;s:12:\"delete_posts\";b:1;}}s:10:\"subscriber\";a:2:{s:4:\"name\";s:10:\"Subscriber\";s:12:\"capabilities\";a:2:{s:4:\"read\";b:1;s:7:\"level_0\";b:1;}}}', 'yes'),
(95, 'fresh_site', '0', 'yes'),
(96, 'widget_search', 'a:2:{i:2;a:1:{s:5:\"title\";s:0:\"\";}s:12:\"_multiwidget\";i:1;}', 'yes'),
(97, 'widget_recent-posts', 'a:2:{i:2;a:2:{s:5:\"title\";s:0:\"\";s:6:\"number\";i:5;}s:12:\"_multiwidget\";i:1;}', 'yes'),
(98, 'widget_recent-comments', 'a:2:{i:2;a:2:{s:5:\"title\";s:0:\"\";s:6:\"number\";i:5;}s:12:\"_multiwidget\";i:1;}', 'yes'),
(99, 'widget_archives', 'a:2:{i:2;a:3:{s:5:\"title\";s:0:\"\";s:5:\"count\";i:0;s:8:\"dropdown\";i:0;}s:12:\"_multiwidget\";i:1;}', 'yes'),
(100, 'widget_meta', 'a:2:{i:2;a:1:{s:5:\"title\";s:0:\"\";}s:12:\"_multiwidget\";i:1;}', 'yes'),
(101, 'sidebars_widgets', 'a:11:{s:19:\"wp_inactive_widgets\";a:0:{}s:9:\"sidebar-1\";a:6:{i:0;s:8:\"search-2\";i:1;s:14:\"recent-posts-2\";i:2;s:17:\"recent-comments-2\";i:3;s:10:\"archives-2\";i:4;s:12:\"categories-2\";i:5;s:6:\"meta-2\";}s:9:\"footer-w1\";a:0:{}s:9:\"footer-w2\";a:0:{}s:9:\"footer-w3\";a:0:{}s:9:\"footer-w4\";a:0:{}s:9:\"footer-w5\";a:0:{}s:9:\"footer-w6\";a:0:{}s:13:\"sub-footer-w1\";a:0:{}s:13:\"sub-footer-w2\";a:0:{}s:13:\"array_version\";i:3;}', 'yes'),
(102, 'widget_pages', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(103, 'widget_calendar', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(104, 'widget_media_audio', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(105, 'widget_media_image', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(106, 'widget_media_gallery', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(107, 'widget_media_video', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(108, 'widget_tag_cloud', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(109, 'widget_nav_menu', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(110, 'widget_custom_html', 'a:1:{s:12:\"_multiwidget\";i:1;}', 'yes'),
(111, 'cron', 'a:4:{i:1556226288;a:4:{s:16:\"wp_version_check\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:10:\"twicedaily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:43200;}}s:17:\"wp_update_plugins\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:10:\"twicedaily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:43200;}}s:16:\"wp_update_themes\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:10:\"twicedaily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:43200;}}s:34:\"wp_privacy_delete_old_export_files\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:6:\"hourly\";s:4:\"args\";a:0:{}s:8:\"interval\";i:3600;}}}i:1556269499;a:2:{s:19:\"wp_scheduled_delete\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:5:\"daily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:86400;}}s:25:\"delete_expired_transients\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:5:\"daily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:86400;}}}i:1556269500;a:1:{s:30:\"wp_scheduled_auto_draft_delete\";a:1:{s:32:\"40cd750bba9870f18aada2478b24840a\";a:3:{s:8:\"schedule\";s:5:\"daily\";s:4:\"args\";a:0:{}s:8:\"interval\";i:86400;}}}s:7:\"version\";i:2;}', 'yes'),
(112, 'theme_mods_twentynineteen', 'a:3:{s:18:\"custom_css_post_id\";i:-1;s:16:\"sidebars_widgets\";a:2:{s:4:\"time\";i:1555338467;s:4:\"data\";a:2:{s:19:\"wp_inactive_widgets\";a:0:{}s:9:\"sidebar-1\";a:6:{i:0;s:8:\"search-2\";i:1;s:14:\"recent-posts-2\";i:2;s:17:\"recent-comments-2\";i:3;s:10:\"archives-2\";i:4;s:12:\"categories-2\";i:5;s:6:\"meta-2\";}}}s:18:\"nav_menu_locations\";a:0:{}}', 'yes'),
(114, '_site_transient_update_core', 'O:8:\"stdClass\":4:{s:7:\"updates\";a:1:{i:0;O:8:\"stdClass\":10:{s:8:\"response\";s:6:\"latest\";s:8:\"download\";s:59:\"https://downloads.wordpress.org/release/wordpress-5.1.1.zip\";s:6:\"locale\";s:5:\"en_US\";s:8:\"packages\";O:8:\"stdClass\":5:{s:4:\"full\";s:59:\"https://downloads.wordpress.org/release/wordpress-5.1.1.zip\";s:10:\"no_content\";s:70:\"https://downloads.wordpress.org/release/wordpress-5.1.1-no-content.zip\";s:11:\"new_bundled\";s:71:\"https://downloads.wordpress.org/release/wordpress-5.1.1-new-bundled.zip\";s:7:\"partial\";b:0;s:8:\"rollback\";b:0;}s:7:\"current\";s:5:\"5.1.1\";s:7:\"version\";s:5:\"5.1.1\";s:11:\"php_version\";s:5:\"5.2.4\";s:13:\"mysql_version\";s:3:\"5.0\";s:11:\"new_bundled\";s:3:\"5.0\";s:15:\"partial_version\";s:0:\"\";}}s:12:\"last_checked\";i:1556188004;s:15:\"version_checked\";s:5:\"5.1.1\";s:12:\"translations\";a:0:{}}', 'no'),
(119, '_site_transient_update_themes', 'O:8:\"stdClass\":4:{s:12:\"last_checked\";i:1556188006;s:7:\"checked\";a:5:{s:4:\"grow\";s:5:\"1.3.8\";s:15:\"plasticoverflow\";s:5:\"1.0.0\";s:14:\"twentynineteen\";s:3:\"1.3\";s:15:\"twentyseventeen\";s:3:\"2.1\";s:13:\"twentysixteen\";s:3:\"1.9\";}s:8:\"response\";a:0:{}s:12:\"translations\";a:0:{}}', 'no'),
(125, 'can_compress_scripts', '0', 'no'),
(176, 'WPLANG', '', 'yes'),
(185, 'recently_activated', 'a:0:{}', 'yes'),
(188, 'new_admin_email', 'marius.mikelsen@hiof.no', 'yes'),
(191, 'current_theme', 'PlasticOverflow', 'yes'),
(192, 'theme_mods_plasticoverflow', 'a:4:{i:0;b:0;s:18:\"nav_menu_locations\";a:1:{s:11:\"header_menu\";i:2;}s:18:\"custom_css_post_id\";i:-1;s:16:\"sidebars_widgets\";a:2:{s:4:\"time\";i:1555338464;s:4:\"data\";a:5:{s:19:\"wp_inactive_widgets\";a:0:{}s:9:\"sidebar-1\";a:6:{i:0;s:8:\"search-2\";i:1;s:14:\"recent-posts-2\";i:2;s:17:\"recent-comments-2\";i:3;s:10:\"archives-2\";i:4;s:12:\"categories-2\";i:5;s:6:\"meta-2\";}s:8:\"footer-1\";a:0:{}s:8:\"footer-2\";a:0:{}s:8:\"footer-3\";a:0:{}}}}', 'yes'),
(193, 'theme_switched', '', 'yes'),
(194, 'thinkup_migrate_slider_page2image', '1', 'yes'),
(197, 'experon_thinkup_notice_welcome', '1', 'yes'),
(207, 'thinkup_redux_variables', 'a:9:{s:29:\"thinkup_homepage_sliderswitch\";s:7:\"option4\";s:27:\"thinkup_header_searchswitch\";s:3:\"off\";s:35:\"thinkup_homepage_sliderimage1_image\";a:1:{s:3:\"url\";s:87:\"https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/hacking_terms_2.jpg\";}s:35:\"thinkup_homepage_sliderimage1_title\";s:22:\"Professional Equipment\";s:34:\"thinkup_homepage_sliderimage1_desc\";s:37:\"For Penetration Testing & Shenanigans\";s:35:\"thinkup_homepage_sliderimage2_image\";a:1:{s:3:\"url\";s:127:\"https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720.jpg\";}s:35:\"thinkup_homepage_sliderimage2_title\";s:20:\"Specialized Software\";s:32:\"thinkup_general_breadcrumbswitch\";i:1;s:34:\"thinkup_homepage_sliderimage2_desc\";s:37:\"For Penetration Testing & Shenanigans\";}', 'yes'),
(231, 'theme_mods_rocked', 'a:4:{i:0;b:0;s:18:\"nav_menu_locations\";a:0:{}s:18:\"custom_css_post_id\";i:-1;s:16:\"sidebars_widgets\";a:2:{s:4:\"time\";i:1555337923;s:4:\"data\";a:5:{s:19:\"wp_inactive_widgets\";a:0:{}s:9:\"sidebar-1\";a:6:{i:0;s:8:\"search-2\";i:1;s:14:\"recent-posts-2\";i:2;s:17:\"recent-comments-2\";i:3;s:10:\"archives-2\";i:4;s:12:\"categories-2\";i:5;s:6:\"meta-2\";}s:8:\"footer-1\";a:0:{}s:8:\"footer-2\";a:0:{}s:8:\"footer-3\";a:0:{}}}}', 'yes'),
(233, '_site_transient_update_plugins', 'O:8:\"stdClass\":5:{s:12:\"last_checked\";i:1556188006;s:7:\"checked\";a:2:{s:19:\"akismet/akismet.php\";s:5:\"4.1.1\";s:9:\"hello.php\";s:5:\"1.7.1\";}s:8:\"response\";a:0:{}s:12:\"translations\";a:0:{}s:9:\"no_update\";a:2:{s:19:\"akismet/akismet.php\";O:8:\"stdClass\":9:{s:2:\"id\";s:21:\"w.org/plugins/akismet\";s:4:\"slug\";s:7:\"akismet\";s:6:\"plugin\";s:19:\"akismet/akismet.php\";s:11:\"new_version\";s:5:\"4.1.1\";s:3:\"url\";s:38:\"https://wordpress.org/plugins/akismet/\";s:7:\"package\";s:56:\"https://downloads.wordpress.org/plugin/akismet.4.1.1.zip\";s:5:\"icons\";a:2:{s:2:\"2x\";s:59:\"https://ps.w.org/akismet/assets/icon-256x256.png?rev=969272\";s:2:\"1x\";s:59:\"https://ps.w.org/akismet/assets/icon-128x128.png?rev=969272\";}s:7:\"banners\";a:1:{s:2:\"1x\";s:61:\"https://ps.w.org/akismet/assets/banner-772x250.jpg?rev=479904\";}s:11:\"banners_rtl\";a:0:{}}s:9:\"hello.php\";O:8:\"stdClass\":9:{s:2:\"id\";s:25:\"w.org/plugins/hello-dolly\";s:4:\"slug\";s:11:\"hello-dolly\";s:6:\"plugin\";s:9:\"hello.php\";s:11:\"new_version\";s:3:\"1.6\";s:3:\"url\";s:42:\"https://wordpress.org/plugins/hello-dolly/\";s:7:\"package\";s:58:\"https://downloads.wordpress.org/plugin/hello-dolly.1.6.zip\";s:5:\"icons\";a:2:{s:2:\"2x\";s:64:\"https://ps.w.org/hello-dolly/assets/icon-256x256.jpg?rev=2052855\";s:2:\"1x\";s:64:\"https://ps.w.org/hello-dolly/assets/icon-128x128.jpg?rev=2052855\";}s:7:\"banners\";a:1:{s:2:\"1x\";s:66:\"https://ps.w.org/hello-dolly/assets/banner-772x250.jpg?rev=2052855\";}s:11:\"banners_rtl\";a:0:{}}}}', 'no'),
(234, 'ftp_credentials', 'a:3:{s:8:\"hostname\";s:14:\"itstud.hiof.no\";s:8:\"username\";s:7:\"mariumi\";s:15:\"connection_type\";s:4:\"ftps\";}', 'yes'),
(241, 'theme_mods_grow', 'a:4:{i:0;b:0;s:18:\"nav_menu_locations\";a:0:{}s:18:\"custom_css_post_id\";i:-1;s:16:\"sidebars_widgets\";a:2:{s:4:\"time\";i:1555338484;s:4:\"data\";a:10:{s:19:\"wp_inactive_widgets\";a:0:{}s:9:\"sidebar-1\";a:6:{i:0;s:8:\"search-2\";i:1;s:14:\"recent-posts-2\";i:2;s:17:\"recent-comments-2\";i:3;s:10:\"archives-2\";i:4;s:12:\"categories-2\";i:5;s:6:\"meta-2\";}s:9:\"footer-w1\";a:0:{}s:9:\"footer-w2\";a:0:{}s:9:\"footer-w3\";a:0:{}s:9:\"footer-w4\";a:0:{}s:9:\"footer-w5\";a:0:{}s:9:\"footer-w6\";a:0:{}s:13:\"sub-footer-w1\";a:0:{}s:13:\"sub-footer-w2\";a:0:{}}}}', 'yes'),
(251, 'nav_menu_options', 'a:2:{i:0;b:0;s:8:\"auto_add\";a:0:{}}', 'yes'),
(302, '_transient_is_multi_author', '0', 'yes'),
(303, '_transient_all_the_cool_cats', '1', 'yes'),
(328, '_site_transient_timeout_theme_roots', '1556189805', 'no'),
(329, '_site_transient_theme_roots', 'a:5:{s:4:\"grow\";s:7:\"/themes\";s:15:\"plasticoverflow\";s:7:\"/themes\";s:14:\"twentynineteen\";s:7:\"/themes\";s:15:\"twentyseventeen\";s:7:\"/themes\";s:13:\"twentysixteen\";s:7:\"/themes\";}', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `uin_postmeta`
--

CREATE TABLE `uin_postmeta` (
  `meta_id` bigint(20) UNSIGNED NOT NULL,
  `post_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_value` longtext COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_postmeta`
--

INSERT INTO `uin_postmeta` (`meta_id`, `post_id`, `meta_key`, `meta_value`) VALUES
(1, 2, '_wp_page_template', 'default'),
(2, 3, '_wp_page_template', 'default'),
(3, 1, '_wp_trash_meta_status', 'publish'),
(4, 1, '_wp_trash_meta_time', '1554973668'),
(5, 1, '_wp_desired_post_slug', 'hello-world'),
(6, 1, '_wp_trash_meta_comments_status', 'a:1:{i:1;s:1:\"1\";}'),
(7, 2, '_wp_trash_meta_status', 'publish'),
(8, 2, '_wp_trash_meta_time', '1554973668'),
(9, 2, '_wp_desired_post_slug', 'sample-page'),
(10, 3, '_wp_trash_meta_status', 'draft'),
(11, 3, '_wp_trash_meta_time', '1554973668'),
(12, 3, '_wp_desired_post_slug', 'privacy-policy'),
(13, 4, '_wp_trash_meta_status', 'auto-draft'),
(14, 4, '_wp_trash_meta_time', '1554973668'),
(15, 4, '_wp_desired_post_slug', ''),
(16, 11, '_edit_lock', '1555067564:2'),
(17, 13, '_edit_lock', '1555249247:2'),
(18, 14, '_wp_attached_file', '2019/04/hacking_terms_2.jpg'),
(19, 14, '_wp_attachment_metadata', 'a:5:{s:5:\"width\";i:698;s:6:\"height\";i:250;s:4:\"file\";s:27:\"2019/04/hacking_terms_2.jpg\";s:5:\"sizes\";a:3:{s:9:\"thumbnail\";a:4:{s:4:\"file\";s:27:\"hacking_terms_2-150x150.jpg\";s:5:\"width\";i:150;s:6:\"height\";i:150;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:6:\"medium\";a:4:{s:4:\"file\";s:27:\"hacking_terms_2-300x107.jpg\";s:5:\"width\";i:300;s:6:\"height\";i:107;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:14:\"sc-testimonial\";a:4:{s:4:\"file\";s:25:\"hacking_terms_2-53x53.jpg\";s:5:\"width\";i:53;s:6:\"height\";i:53;s:9:\"mime-type\";s:10:\"image/jpeg\";}}s:10:\"image_meta\";a:12:{s:8:\"aperture\";s:1:\"0\";s:6:\"credit\";s:0:\"\";s:6:\"camera\";s:0:\"\";s:7:\"caption\";s:0:\"\";s:17:\"created_timestamp\";s:1:\"0\";s:9:\"copyright\";s:0:\"\";s:12:\"focal_length\";s:1:\"0\";s:3:\"iso\";s:1:\"0\";s:13:\"shutter_speed\";s:1:\"0\";s:5:\"title\";s:0:\"\";s:11:\"orientation\";s:1:\"1\";s:8:\"keywords\";a:0:{}}}'),
(20, 15, '_wp_attached_file', '2019/04/pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720.jpg'),
(21, 15, '_wp_attachment_metadata', 'a:5:{s:5:\"width\";i:720;s:6:\"height\";i:405;s:4:\"file\";s:67:\"2019/04/pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720.jpg\";s:5:\"sizes\";a:5:{s:9:\"thumbnail\";a:4:{s:4:\"file\";s:67:\"pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720-150x150.jpg\";s:5:\"width\";i:150;s:6:\"height\";i:150;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:6:\"medium\";a:4:{s:4:\"file\";s:67:\"pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720-300x169.jpg\";s:5:\"width\";i:300;s:6:\"height\";i:169;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:14:\"sc-testimonial\";a:4:{s:4:\"file\";s:65:\"pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720-53x53.jpg\";s:5:\"width\";i:53;s:6:\"height\";i:53;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:11:\"column1-1/3\";a:4:{s:4:\"file\";s:67:\"pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720-720x380.jpg\";s:5:\"width\";i:720;s:6:\"height\";i:380;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:11:\"column1-1/4\";a:4:{s:4:\"file\";s:67:\"pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720-720x285.jpg\";s:5:\"width\";i:720;s:6:\"height\";i:285;s:9:\"mime-type\";s:10:\"image/jpeg\";}}s:10:\"image_meta\";a:12:{s:8:\"aperture\";s:1:\"0\";s:6:\"credit\";s:0:\"\";s:6:\"camera\";s:0:\"\";s:7:\"caption\";s:0:\"\";s:17:\"created_timestamp\";s:1:\"0\";s:9:\"copyright\";s:0:\"\";s:12:\"focal_length\";s:1:\"0\";s:3:\"iso\";s:1:\"0\";s:13:\"shutter_speed\";s:1:\"0\";s:5:\"title\";s:0:\"\";s:11:\"orientation\";s:1:\"0\";s:8:\"keywords\";a:0:{}}}'),
(22, 13, '_wp_trash_meta_status', 'publish'),
(23, 13, '_wp_trash_meta_time', '1555075318'),
(24, 16, '_edit_lock', '1555338626:2'),
(25, 16, '_wp_trash_meta_status', 'publish'),
(26, 16, '_wp_trash_meta_time', '1555338641'),
(27, 17, '_edit_lock', '1555338661:2'),
(30, 19, '_edit_lock', '1555488709:2'),
(31, 21, '_edit_lock', '1555338765:2'),
(32, 23, '_menu_item_type', 'post_type'),
(33, 23, '_menu_item_menu_item_parent', '0'),
(34, 23, '_menu_item_object_id', '21'),
(35, 23, '_menu_item_object', 'page'),
(36, 23, '_menu_item_target', ''),
(37, 23, '_menu_item_classes', 'a:1:{i:0;s:0:\"\";}'),
(38, 23, '_menu_item_xfn', ''),
(39, 23, '_menu_item_url', ''),
(41, 24, '_menu_item_type', 'post_type'),
(42, 24, '_menu_item_menu_item_parent', '0'),
(43, 24, '_menu_item_object_id', '19'),
(44, 24, '_menu_item_object', 'page'),
(45, 24, '_menu_item_target', ''),
(46, 24, '_menu_item_classes', 'a:1:{i:0;s:0:\"\";}'),
(47, 24, '_menu_item_xfn', ''),
(48, 24, '_menu_item_url', ''),
(50, 25, '_menu_item_type', 'post_type'),
(51, 25, '_menu_item_menu_item_parent', '0'),
(52, 25, '_menu_item_object_id', '11'),
(53, 25, '_menu_item_object', 'page'),
(54, 25, '_menu_item_target', ''),
(55, 25, '_menu_item_classes', 'a:1:{i:0;s:0:\"\";}'),
(56, 25, '_menu_item_xfn', ''),
(57, 25, '_menu_item_url', ''),
(59, 26, '_menu_item_type', 'custom'),
(60, 26, '_menu_item_menu_item_parent', '0'),
(61, 26, '_menu_item_object_id', '26'),
(62, 26, '_menu_item_object', 'custom'),
(63, 26, '_menu_item_target', ''),
(64, 26, '_menu_item_classes', 'a:1:{i:0;s:0:\"\";}'),
(65, 26, '_menu_item_xfn', ''),
(66, 26, '_menu_item_url', 'https://itstud.hiof.no/uinv19/uinv19gr27/'),
(68, 19, '_wp_page_template', 'page-products.php');

-- --------------------------------------------------------

--
-- Table structure for table `uin_posts`
--

CREATE TABLE `uin_posts` (
  `ID` bigint(20) UNSIGNED NOT NULL,
  `post_author` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `post_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `post_date_gmt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `post_content` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_title` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_excerpt` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'publish',
  `comment_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'open',
  `ping_status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'open',
  `post_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `post_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `to_ping` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `pinged` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `post_modified_gmt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `post_content_filtered` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `post_parent` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `guid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `menu_order` int(11) NOT NULL DEFAULT '0',
  `post_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'post',
  `post_mime_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `comment_count` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_posts`
--

INSERT INTO `uin_posts` (`ID`, `post_author`, `post_date`, `post_date_gmt`, `post_content`, `post_title`, `post_excerpt`, `post_status`, `comment_status`, `ping_status`, `post_password`, `post_name`, `to_ping`, `pinged`, `post_modified`, `post_modified_gmt`, `post_content_filtered`, `post_parent`, `guid`, `menu_order`, `post_type`, `post_mime_type`, `comment_count`) VALUES
(1, 1, '2019-04-11 09:04:48', '2019-04-11 09:04:48', '<!-- wp:paragraph -->\n<p>Welcome to WordPress. This is your first post. Edit or delete it, then start writing!</p>\n<!-- /wp:paragraph -->', 'Hello world!', '', 'trash', 'open', 'open', '', 'hello-world__trashed', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=1', 0, 'post', '', 1),
(2, 1, '2019-04-11 09:04:48', '2019-04-11 09:04:48', '<!-- wp:paragraph -->\n<p>This is an example page. It\'s different from a blog post because it will stay in one place and will show up in your site navigation (in most themes). Most people start with an About page that introduces them to potential site visitors. It might say something like this:</p>\n<!-- /wp:paragraph -->\n\n<!-- wp:quote -->\n<blockquote class=\"wp-block-quote\"><p>Hi there! I\'m a bike messenger by day, aspiring actor by night, and this is my website. I live in Los Angeles, have a great dog named Jack, and I like pi&#241;a coladas. (And gettin\' caught in the rain.)</p></blockquote>\n<!-- /wp:quote -->\n\n<!-- wp:paragraph -->\n<p>...or something like this:</p>\n<!-- /wp:paragraph -->\n\n<!-- wp:quote -->\n<blockquote class=\"wp-block-quote\"><p>The XYZ Doohickey Company was founded in 1971, and has been providing quality doohickeys to the public ever since. Located in Gotham City, XYZ employs over 2,000 people and does all kinds of awesome things for the Gotham community.</p></blockquote>\n<!-- /wp:quote -->\n\n<!-- wp:paragraph -->\n<p>As a new WordPress user, you should go to <a href=\"https://itstud.hiof.no/uinv19/uinv19gr27/wp-admin/\">your dashboard</a> to delete this page and create new pages for your content. Have fun!</p>\n<!-- /wp:paragraph -->', 'Sample Page', '', 'trash', 'closed', 'open', '', 'sample-page__trashed', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?page_id=2', 0, 'page', '', 0),
(3, 1, '2019-04-11 09:04:48', '2019-04-11 09:04:48', '<!-- wp:heading --><h2>Who we are</h2><!-- /wp:heading --><!-- wp:paragraph --><p>Our website address is: https://itstud.hiof.no/uinv19/uinv19gr27.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>What personal data we collect and why we collect it</h2><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Comments</h3><!-- /wp:heading --><!-- wp:paragraph --><p>When visitors leave comments on the site we collect the data shown in the comments form, and also the visitor&#8217;s IP address and browser user agent string to help spam detection.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>An anonymized string created from your email address (also called a hash) may be provided to the Gravatar service to see if you are using it. The Gravatar service privacy policy is available here: https://automattic.com/privacy/. After approval of your comment, your profile picture is visible to the public in the context of your comment.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Media</h3><!-- /wp:heading --><!-- wp:paragraph --><p>If you upload images to the website, you should avoid uploading images with embedded location data (EXIF GPS) included. Visitors to the website can download and extract any location data from images on the website.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Contact forms</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Cookies</h3><!-- /wp:heading --><!-- wp:paragraph --><p>If you leave a comment on our site you may opt-in to saving your name, email address and website in cookies. These are for your convenience so that you do not have to fill in your details again when you leave another comment. These cookies will last for one year.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>If you have an account and you log in to this site, we will set a temporary cookie to determine if your browser accepts cookies. This cookie contains no personal data and is discarded when you close your browser.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>When you log in, we will also set up several cookies to save your login information and your screen display choices. Login cookies last for two days, and screen options cookies last for a year. If you select &quot;Remember Me&quot;, your login will persist for two weeks. If you log out of your account, the login cookies will be removed.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>If you edit or publish an article, an additional cookie will be saved in your browser. This cookie includes no personal data and simply indicates the post ID of the article you just edited. It expires after 1 day.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Embedded content from other websites</h3><!-- /wp:heading --><!-- wp:paragraph --><p>Articles on this site may include embedded content (e.g. videos, images, articles, etc.). Embedded content from other websites behaves in the exact same way as if the visitor has visited the other website.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>These websites may collect data about you, use cookies, embed additional third-party tracking, and monitor your interaction with that embedded content, including tracking your interaction with the embedded content if you have an account and are logged in to that website.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Analytics</h3><!-- /wp:heading --><!-- wp:heading --><h2>Who we share your data with</h2><!-- /wp:heading --><!-- wp:heading --><h2>How long we retain your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>If you leave a comment, the comment and its metadata are retained indefinitely. This is so we can recognize and approve any follow-up comments automatically instead of holding them in a moderation queue.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>For users that register on our website (if any), we also store the personal information they provide in their user profile. All users can see, edit, or delete their personal information at any time (except they cannot change their username). Website administrators can also see and edit that information.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>What rights you have over your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>If you have an account on this site, or have left comments, you can request to receive an exported file of the personal data we hold about you, including any data you have provided to us. You can also request that we erase any personal data we hold about you. This does not include any data we are obliged to keep for administrative, legal, or security purposes.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>Where we send your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>Visitor comments may be checked through an automated spam detection service.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>Your contact information</h2><!-- /wp:heading --><!-- wp:heading --><h2>Additional information</h2><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>How we protect your data</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What data breach procedures we have in place</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What third parties we receive data from</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What automated decision making and/or profiling we do with user data</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Industry regulatory disclosure requirements</h3><!-- /wp:heading -->', 'Privacy Policy', '', 'trash', 'closed', 'open', '', 'privacy-policy__trashed', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?page_id=3', 0, 'page', '', 0),
(4, 1, '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 'Auto Draft', '', 'trash', 'open', 'open', '', '__trashed', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=4', 0, 'post', '', 0),
(6, 2, '2019-04-11 09:07:48', '2019-04-11 09:07:48', '<!-- wp:paragraph -->\n<p>Welcome to WordPress. This is your first post. Edit or delete it, then start writing!</p>\n<!-- /wp:paragraph -->', 'Hello world!', '', 'inherit', 'closed', 'closed', '', '1-revision-v1', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 1, 'https://itstud.hiof.no/uinv19/uinv19gr27/2019/04/11/1-revision-v1/', 0, 'revision', '', 0),
(7, 2, '2019-04-11 09:07:48', '2019-04-11 09:07:48', '<!-- wp:paragraph -->\n<p>This is an example page. It\'s different from a blog post because it will stay in one place and will show up in your site navigation (in most themes). Most people start with an About page that introduces them to potential site visitors. It might say something like this:</p>\n<!-- /wp:paragraph -->\n\n<!-- wp:quote -->\n<blockquote class=\"wp-block-quote\"><p>Hi there! I\'m a bike messenger by day, aspiring actor by night, and this is my website. I live in Los Angeles, have a great dog named Jack, and I like pi&#241;a coladas. (And gettin\' caught in the rain.)</p></blockquote>\n<!-- /wp:quote -->\n\n<!-- wp:paragraph -->\n<p>...or something like this:</p>\n<!-- /wp:paragraph -->\n\n<!-- wp:quote -->\n<blockquote class=\"wp-block-quote\"><p>The XYZ Doohickey Company was founded in 1971, and has been providing quality doohickeys to the public ever since. Located in Gotham City, XYZ employs over 2,000 people and does all kinds of awesome things for the Gotham community.</p></blockquote>\n<!-- /wp:quote -->\n\n<!-- wp:paragraph -->\n<p>As a new WordPress user, you should go to <a href=\"https://itstud.hiof.no/uinv19/uinv19gr27/wp-admin/\">your dashboard</a> to delete this page and create new pages for your content. Have fun!</p>\n<!-- /wp:paragraph -->', 'Sample Page', '', 'inherit', 'closed', 'closed', '', '2-revision-v1', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 2, 'https://itstud.hiof.no/uinv19/uinv19gr27/2019/04/11/2-revision-v1/', 0, 'revision', '', 0),
(8, 2, '2019-04-11 09:07:48', '2019-04-11 09:07:48', '<!-- wp:heading --><h2>Who we are</h2><!-- /wp:heading --><!-- wp:paragraph --><p>Our website address is: https://itstud.hiof.no/uinv19/uinv19gr27.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>What personal data we collect and why we collect it</h2><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Comments</h3><!-- /wp:heading --><!-- wp:paragraph --><p>When visitors leave comments on the site we collect the data shown in the comments form, and also the visitor&#8217;s IP address and browser user agent string to help spam detection.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>An anonymized string created from your email address (also called a hash) may be provided to the Gravatar service to see if you are using it. The Gravatar service privacy policy is available here: https://automattic.com/privacy/. After approval of your comment, your profile picture is visible to the public in the context of your comment.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Media</h3><!-- /wp:heading --><!-- wp:paragraph --><p>If you upload images to the website, you should avoid uploading images with embedded location data (EXIF GPS) included. Visitors to the website can download and extract any location data from images on the website.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Contact forms</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Cookies</h3><!-- /wp:heading --><!-- wp:paragraph --><p>If you leave a comment on our site you may opt-in to saving your name, email address and website in cookies. These are for your convenience so that you do not have to fill in your details again when you leave another comment. These cookies will last for one year.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>If you have an account and you log in to this site, we will set a temporary cookie to determine if your browser accepts cookies. This cookie contains no personal data and is discarded when you close your browser.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>When you log in, we will also set up several cookies to save your login information and your screen display choices. Login cookies last for two days, and screen options cookies last for a year. If you select &quot;Remember Me&quot;, your login will persist for two weeks. If you log out of your account, the login cookies will be removed.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>If you edit or publish an article, an additional cookie will be saved in your browser. This cookie includes no personal data and simply indicates the post ID of the article you just edited. It expires after 1 day.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Embedded content from other websites</h3><!-- /wp:heading --><!-- wp:paragraph --><p>Articles on this site may include embedded content (e.g. videos, images, articles, etc.). Embedded content from other websites behaves in the exact same way as if the visitor has visited the other website.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>These websites may collect data about you, use cookies, embed additional third-party tracking, and monitor your interaction with that embedded content, including tracking your interaction with the embedded content if you have an account and are logged in to that website.</p><!-- /wp:paragraph --><!-- wp:heading {\"level\":3} --><h3>Analytics</h3><!-- /wp:heading --><!-- wp:heading --><h2>Who we share your data with</h2><!-- /wp:heading --><!-- wp:heading --><h2>How long we retain your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>If you leave a comment, the comment and its metadata are retained indefinitely. This is so we can recognize and approve any follow-up comments automatically instead of holding them in a moderation queue.</p><!-- /wp:paragraph --><!-- wp:paragraph --><p>For users that register on our website (if any), we also store the personal information they provide in their user profile. All users can see, edit, or delete their personal information at any time (except they cannot change their username). Website administrators can also see and edit that information.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>What rights you have over your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>If you have an account on this site, or have left comments, you can request to receive an exported file of the personal data we hold about you, including any data you have provided to us. You can also request that we erase any personal data we hold about you. This does not include any data we are obliged to keep for administrative, legal, or security purposes.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>Where we send your data</h2><!-- /wp:heading --><!-- wp:paragraph --><p>Visitor comments may be checked through an automated spam detection service.</p><!-- /wp:paragraph --><!-- wp:heading --><h2>Your contact information</h2><!-- /wp:heading --><!-- wp:heading --><h2>Additional information</h2><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>How we protect your data</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What data breach procedures we have in place</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What third parties we receive data from</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>What automated decision making and/or profiling we do with user data</h3><!-- /wp:heading --><!-- wp:heading {\"level\":3} --><h3>Industry regulatory disclosure requirements</h3><!-- /wp:heading -->', 'Privacy Policy', '', 'inherit', 'closed', 'closed', '', '3-revision-v1', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 3, 'https://itstud.hiof.no/uinv19/uinv19gr27/2019/04/11/3-revision-v1/', 0, 'revision', '', 0),
(9, 2, '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 'Auto Draft', '', 'inherit', 'closed', 'closed', '', '4-revision-v1', '', '', '2019-04-11 09:07:48', '2019-04-11 09:07:48', '', 4, 'https://itstud.hiof.no/uinv19/uinv19gr27/2019/04/11/4-revision-v1/', 0, 'revision', '', 0),
(11, 2, '2019-04-12 11:15:04', '2019-04-12 11:15:04', '<!-- wp:paragraph -->\n<p>This is the about us section</p>\n<!-- /wp:paragraph -->', 'About', '', 'publish', 'closed', 'closed', '', 'about', '', '', '2019-04-12 11:15:04', '2019-04-12 11:15:04', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?page_id=11', 0, 'page', '', 0),
(12, 2, '2019-04-12 11:15:04', '2019-04-12 11:15:04', '<!-- wp:paragraph -->\n<p>This is the about us section</p>\n<!-- /wp:paragraph -->', 'About', '', 'inherit', 'closed', 'closed', '', '11-revision-v1', '', '', '2019-04-12 11:15:04', '2019-04-12 11:15:04', '', 11, 'https://itstud.hiof.no/uinv19/uinv19gr27/11-revision-v1/', 0, 'revision', '', 0),
(13, 2, '2019-04-12 13:21:58', '2019-04-12 13:21:58', '{\n    \"thinkup_redux_variables[thinkup_homepage_sliderswitch]\": {\n        \"value\": \"option4\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:17:47\"\n    },\n    \"thinkup_redux_variables[thinkup_header_searchswitch]\": {\n        \"value\": \"off\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:17:47\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage1_image][url]\": {\n        \"value\": \"https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/hacking_terms_2.jpg\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:20:47\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage1_title]\": {\n        \"value\": \"Professional Equipment\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:21:47\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage1_desc]\": {\n        \"value\": \"For Penetration Testing\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:21:47\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage2_image][url]\": {\n        \"value\": \"https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720.jpg\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:21:47\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage2_title]\": {\n        \"value\": \"Specialized Software\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-12 13:21:58\"\n    }\n}', '', '', 'trash', 'closed', 'closed', '', '442cfaeb-c48d-4623-9e6c-b1b6a2e87ab8', '', '', '2019-04-12 13:21:58', '2019-04-12 13:21:58', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=13', 0, 'customize_changeset', '', 0),
(14, 2, '2019-04-12 13:20:06', '2019-04-12 13:20:06', '', 'hacking_terms_2', '', 'inherit', 'open', 'closed', '', 'hacking_terms_2', '', '', '2019-04-12 13:20:06', '2019-04-12 13:20:06', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/hacking_terms_2.jpg', 0, 'attachment', 'image/jpeg', 0),
(15, 2, '2019-04-12 13:21:41', '2019-04-12 13:21:41', '', 'pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720', '', 'inherit', 'open', 'closed', '', 'pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720', '', '', '2019-04-12 13:21:41', '2019-04-12 13:21:41', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/wp-content/uploads/2019/04/pwstudio123rfhttpswww-123rf-comprofile_pwstudio-720x720.jpg', 0, 'attachment', 'image/jpeg', 0),
(16, 2, '2019-04-15 14:30:41', '2019-04-15 14:30:41', '{\n    \"thinkup_redux_variables[thinkup_general_breadcrumbswitch]\": {\n        \"value\": \"1\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-15 14:29:43\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage1_desc]\": {\n        \"value\": \"For Penetration Testing & Shenanigans\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-15 14:29:43\"\n    },\n    \"thinkup_redux_variables[thinkup_homepage_sliderimage2_desc]\": {\n        \"value\": \"For Penetration Testing & Shenanigans\",\n        \"type\": \"option\",\n        \"user_id\": 2,\n        \"date_modified_gmt\": \"2019-04-15 14:30:41\"\n    }\n}', '', '', 'trash', 'closed', 'closed', '', 'cceed7ec-e3ba-4121-b3b4-74b94a2dee94', '', '', '2019-04-15 14:30:41', '2019-04-15 14:30:41', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=16', 0, 'customize_changeset', '', 0),
(17, 2, '2019-04-15 14:31:39', '2019-04-15 14:31:39', '<!-- wp:paragraph -->\n<p>Hi all followers. Now we have a brand new web store. Why not leave all your cash here, so that we may take good care of them ;)</p>\n<!-- /wp:paragraph -->', 'New Webstore', '', 'publish', 'open', 'open', '', 'new-webstore', '', '', '2019-04-15 14:31:39', '2019-04-15 14:31:39', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=17', 0, 'post', '', 0),
(18, 2, '2019-04-15 14:31:39', '2019-04-15 14:31:39', '<!-- wp:paragraph -->\n<p>Hi all followers. Now we have a brand new web store. Why not leave all your cash here, so that we may take good care of them ;)</p>\n<!-- /wp:paragraph -->', 'New Webstore', '', 'inherit', 'closed', 'closed', '', '17-revision-v1', '', '', '2019-04-15 14:31:39', '2019-04-15 14:31:39', '', 17, 'https://itstud.hiof.no/uinv19/uinv19gr27/17-revision-v1/', 0, 'revision', '', 0),
(19, 2, '2019-04-15 14:32:41', '2019-04-15 14:32:41', '', 'Products', '', 'publish', 'closed', 'closed', '', 'products', '', '', '2019-04-15 16:39:33', '2019-04-15 16:39:33', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?page_id=19', 0, 'page', '', 0),
(20, 2, '2019-04-15 14:32:41', '2019-04-15 14:32:41', '', 'Products', '', 'inherit', 'closed', 'closed', '', '19-revision-v1', '', '', '2019-04-15 14:32:41', '2019-04-15 14:32:41', '', 19, 'https://itstud.hiof.no/uinv19/uinv19gr27/19-revision-v1/', 0, 'revision', '', 0),
(21, 2, '2019-04-15 14:32:51', '2019-04-15 14:32:51', '', 'Contact', '', 'publish', 'closed', 'closed', '', 'contact', '', '', '2019-04-15 14:32:51', '2019-04-15 14:32:51', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?page_id=21', 0, 'page', '', 0),
(22, 2, '2019-04-15 14:32:51', '2019-04-15 14:32:51', '', 'Contact', '', 'inherit', 'closed', 'closed', '', '21-revision-v1', '', '', '2019-04-15 14:32:51', '2019-04-15 14:32:51', '', 21, 'https://itstud.hiof.no/uinv19/uinv19gr27/21-revision-v1/', 0, 'revision', '', 0),
(23, 2, '2019-04-15 14:33:38', '2019-04-15 14:33:38', ' ', '', '', 'publish', 'closed', 'closed', '', '23', '', '', '2019-04-15 14:35:05', '2019-04-15 14:35:05', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=23', 4, 'nav_menu_item', '', 0),
(24, 2, '2019-04-15 14:33:38', '2019-04-15 14:33:38', ' ', '', '', 'publish', 'closed', 'closed', '', '24', '', '', '2019-04-15 14:35:05', '2019-04-15 14:35:05', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=24', 3, 'nav_menu_item', '', 0),
(25, 2, '2019-04-15 14:33:38', '2019-04-15 14:33:38', ' ', '', '', 'publish', 'closed', 'closed', '', '25', '', '', '2019-04-15 14:35:05', '2019-04-15 14:35:05', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=25', 2, 'nav_menu_item', '', 0),
(26, 2, '2019-04-15 14:35:05', '2019-04-15 14:35:05', '', 'Home', '', 'publish', 'closed', 'closed', '', 'home', '', '', '2019-04-15 14:35:05', '2019-04-15 14:35:05', '', 0, 'https://itstud.hiof.no/uinv19/uinv19gr27/?p=26', 1, 'nav_menu_item', '', 0),
(43, 2, '2019-04-17 08:36:19', '2019-04-17 08:36:19', '', 'Products', '', 'inherit', 'closed', 'closed', '', '19-autosave-v1', '', '', '2019-04-17 08:36:19', '2019-04-17 08:36:19', '', 19, 'https://itstud.hiof.no/uinv19/uinv19gr27/19-autosave-v1/', 0, 'revision', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `uin_termmeta`
--

CREATE TABLE `uin_termmeta` (
  `meta_id` bigint(20) UNSIGNED NOT NULL,
  `term_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_value` longtext COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `uin_terms`
--

CREATE TABLE `uin_terms` (
  `term_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `slug` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `term_group` bigint(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_terms`
--

INSERT INTO `uin_terms` (`term_id`, `name`, `slug`, `term_group`) VALUES
(1, 'Uncategorized', 'uncategorized', 0),
(2, 'Main Menu', 'main-menu', 0);

-- --------------------------------------------------------

--
-- Table structure for table `uin_term_relationships`
--

CREATE TABLE `uin_term_relationships` (
  `object_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `term_taxonomy_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `term_order` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_term_relationships`
--

INSERT INTO `uin_term_relationships` (`object_id`, `term_taxonomy_id`, `term_order`) VALUES
(1, 1, 0),
(4, 1, 0),
(17, 1, 0),
(23, 2, 0),
(24, 2, 0),
(25, 2, 0),
(26, 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `uin_term_taxonomy`
--

CREATE TABLE `uin_term_taxonomy` (
  `term_taxonomy_id` bigint(20) UNSIGNED NOT NULL,
  `term_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `taxonomy` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `description` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `count` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_term_taxonomy`
--

INSERT INTO `uin_term_taxonomy` (`term_taxonomy_id`, `term_id`, `taxonomy`, `description`, `parent`, `count`) VALUES
(1, 1, 'category', '', 0, 1),
(2, 2, 'nav_menu', '', 0, 4);

-- --------------------------------------------------------

--
-- Table structure for table `uin_usermeta`
--

CREATE TABLE `uin_usermeta` (
  `umeta_id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL DEFAULT '0',
  `meta_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meta_value` longtext COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_usermeta`
--

INSERT INTO `uin_usermeta` (`umeta_id`, `user_id`, `meta_key`, `meta_value`) VALUES
(19, 2, 'nickname', 'xzy'),
(20, 2, 'first_name', 'Marius'),
(21, 2, 'last_name', 'Mikelsen'),
(22, 2, 'description', ''),
(23, 2, 'rich_editing', 'true'),
(24, 2, 'syntax_highlighting', 'true'),
(25, 2, 'comment_shortcuts', 'false'),
(26, 2, 'admin_color', 'fresh'),
(27, 2, 'use_ssl', '0'),
(28, 2, 'show_admin_bar_front', 'true'),
(29, 2, 'locale', ''),
(30, 2, 'uin_capabilities', 'a:1:{s:13:\"administrator\";b:1;}'),
(31, 2, 'uin_user_level', '10'),
(32, 2, 'dismissed_wp_pointers', 'wp496_privacy'),
(33, 3, 'nickname', 'henrin'),
(34, 3, 'first_name', 'Henrik'),
(35, 3, 'last_name', 'Nilsen'),
(36, 3, 'description', ''),
(37, 3, 'rich_editing', 'true'),
(38, 3, 'syntax_highlighting', 'true'),
(39, 3, 'comment_shortcuts', 'false'),
(40, 3, 'admin_color', 'fresh'),
(41, 3, 'use_ssl', '0'),
(42, 3, 'show_admin_bar_front', 'true'),
(43, 3, 'locale', ''),
(44, 3, 'uin_capabilities', 'a:1:{s:13:\"administrator\";b:1;}'),
(45, 3, 'uin_user_level', '10'),
(46, 3, 'dismissed_wp_pointers', 'wp496_privacy'),
(47, 2, 'default_password_nag', ''),
(48, 2, 'session_tokens', 'a:3:{s:64:\"d28579c3d8ea171a39ae2a0521d2502f6127c497d95496122d0c0539df53189f\";a:4:{s:10:\"expiration\";i:1556183249;s:2:\"ip\";s:22:\"2001:700:a00:ff81::27f\";s:2:\"ua\";s:114:\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\";s:5:\"login\";i:1554973649;}s:64:\"d93152b3b0ed0fe4d498c1a352f2474bef56eff4365514408232adfe39829efb\";a:4:{s:10:\"expiration\";i:1555510658;s:2:\"ip\";s:20:\"2001:700:a00:ffe3::a\";s:2:\"ua\";s:115:\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\";s:5:\"login\";i:1555337858;}s:64:\"66ed38c513d25905fd0ceec0cb1b727bbe1a66135184a6994ee2837229f59e0d\";a:4:{s:10:\"expiration\";i:1556700778;s:2:\"ip\";s:9:\"127.0.0.1\";s:2:\"ua\";s:115:\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\";s:5:\"login\";i:1555491178;}}'),
(49, 2, 'uin_dashboard_quick_press_last_post_id', '5'),
(50, 2, 'community-events-location', 'a:1:{s:2:\"ip\";s:9:\"127.0.0.0\";}'),
(51, 3, 'default_password_nag', ''),
(52, 3, 'session_tokens', 'a:2:{s:64:\"c2f8e0176f06b246aff50338f95be06d0e88a923ffa85b22a9198bd4e1156304\";a:4:{s:10:\"expiration\";i:1555151758;s:2:\"ip\";s:14:\"85.165.201.209\";s:2:\"ua\";s:114:\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36\";s:5:\"login\";i:1554978958;}s:64:\"573e821e010aa7ae1c0ac69e657ae684cd550652ca811104ff33b31bb47011d0\";a:4:{s:10:\"expiration\";i:1555233841;s:2:\"ip\";s:22:\"2001:700:a00:ff81::69f\";s:2:\"ua\";s:115:\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\";s:5:\"login\";i:1555061041;}}'),
(53, 3, 'uin_dashboard_quick_press_last_post_id', '10'),
(54, 3, 'community-events-location', 'a:1:{s:2:\"ip\";s:19:\"2001:700:a00:ff81::\";}'),
(55, 2, 'managenav-menuscolumnshidden', 'a:5:{i:0;s:11:\"link-target\";i:1;s:11:\"css-classes\";i:2;s:3:\"xfn\";i:3;s:11:\"description\";i:4;s:15:\"title-attribute\";}'),
(56, 2, 'metaboxhidden_nav-menus', 'a:2:{i:0;s:12:\"add-post_tag\";i:1;s:15:\"add-post_format\";}'),
(57, 2, 'nav_menu_recently_edited', '2');

-- --------------------------------------------------------

--
-- Table structure for table `uin_users`
--

CREATE TABLE `uin_users` (
  `ID` bigint(20) UNSIGNED NOT NULL,
  `user_login` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_pass` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_nicename` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_url` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_registered` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_activation_key` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_status` int(11) NOT NULL DEFAULT '0',
  `display_name` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `uin_users`
--

INSERT INTO `uin_users` (`ID`, `user_login`, `user_pass`, `user_nicename`, `user_email`, `user_url`, `user_registered`, `user_activation_key`, `user_status`, `display_name`) VALUES
(2, 'xzy', '$P$BOYOrnSm9Q2TJJ5sSAt2hGHMwI97VP.', 'xzy', 'marius.mikelsen@hiof.no', 'http://itstud.hiof.no/~mariumi', '2019-04-11 09:05:49', '', 0, 'Marius Mikelsen'),
(3, 'henrin', '$P$BbOB46wbw1PHYor44CrzZFHn.0SsOz/', 'henrin', 'henrin@hiof.no', 'http://itstud.hiof.no/~henrin', '2019-04-11 09:06:20', '', 0, 'Henrik Nilsen');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `syst_color`
--
ALTER TABLE `syst_color`
  ADD PRIMARY KEY (`color_id`),
  ADD UNIQUE KEY `color_id` (`color_id`);

--
-- Indexes for table `syst_model`
--
ALTER TABLE `syst_model`
  ADD PRIMARY KEY (`model_id`),
  ADD UNIQUE KEY `model_id` (`model_id`),
  ADD UNIQUE KEY `model_name` (`model_name`);

--
-- Indexes for table `syst_products`
--
ALTER TABLE `syst_products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `type_id` (`type_id`);

--
-- Indexes for table `syst_product_has_variants`
--
ALTER TABLE `syst_product_has_variants`
  ADD PRIMARY KEY (`product_id`,`model_id`,`color_id`),
  ADD KEY `color_id` (`color_id`),
  ADD KEY `model_id` (`model_id`);

--
-- Indexes for table `syst_product_type`
--
ALTER TABLE `syst_product_type`
  ADD PRIMARY KEY (`type_id`),
  ADD UNIQUE KEY `type_id` (`type_id`);

--
-- Indexes for table `uin_commentmeta`
--
ALTER TABLE `uin_commentmeta`
  ADD PRIMARY KEY (`meta_id`),
  ADD KEY `comment_id` (`comment_id`),
  ADD KEY `meta_key` (`meta_key`(191));

--
-- Indexes for table `uin_comments`
--
ALTER TABLE `uin_comments`
  ADD PRIMARY KEY (`comment_ID`),
  ADD KEY `comment_post_ID` (`comment_post_ID`),
  ADD KEY `comment_approved_date_gmt` (`comment_approved`,`comment_date_gmt`),
  ADD KEY `comment_date_gmt` (`comment_date_gmt`),
  ADD KEY `comment_parent` (`comment_parent`),
  ADD KEY `comment_author_email` (`comment_author_email`(10));

--
-- Indexes for table `uin_links`
--
ALTER TABLE `uin_links`
  ADD PRIMARY KEY (`link_id`),
  ADD KEY `link_visible` (`link_visible`);

--
-- Indexes for table `uin_options`
--
ALTER TABLE `uin_options`
  ADD PRIMARY KEY (`option_id`),
  ADD UNIQUE KEY `option_name` (`option_name`);

--
-- Indexes for table `uin_postmeta`
--
ALTER TABLE `uin_postmeta`
  ADD PRIMARY KEY (`meta_id`),
  ADD KEY `post_id` (`post_id`),
  ADD KEY `meta_key` (`meta_key`(191));

--
-- Indexes for table `uin_posts`
--
ALTER TABLE `uin_posts`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `post_name` (`post_name`(191)),
  ADD KEY `type_status_date` (`post_type`,`post_status`,`post_date`,`ID`),
  ADD KEY `post_parent` (`post_parent`),
  ADD KEY `post_author` (`post_author`);

--
-- Indexes for table `uin_termmeta`
--
ALTER TABLE `uin_termmeta`
  ADD PRIMARY KEY (`meta_id`),
  ADD KEY `term_id` (`term_id`),
  ADD KEY `meta_key` (`meta_key`(191));

--
-- Indexes for table `uin_terms`
--
ALTER TABLE `uin_terms`
  ADD PRIMARY KEY (`term_id`),
  ADD KEY `slug` (`slug`(191)),
  ADD KEY `name` (`name`(191));

--
-- Indexes for table `uin_term_relationships`
--
ALTER TABLE `uin_term_relationships`
  ADD PRIMARY KEY (`object_id`,`term_taxonomy_id`),
  ADD KEY `term_taxonomy_id` (`term_taxonomy_id`);

--
-- Indexes for table `uin_term_taxonomy`
--
ALTER TABLE `uin_term_taxonomy`
  ADD PRIMARY KEY (`term_taxonomy_id`),
  ADD UNIQUE KEY `term_id_taxonomy` (`term_id`,`taxonomy`),
  ADD KEY `taxonomy` (`taxonomy`);

--
-- Indexes for table `uin_usermeta`
--
ALTER TABLE `uin_usermeta`
  ADD PRIMARY KEY (`umeta_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `meta_key` (`meta_key`(191));

--
-- Indexes for table `uin_users`
--
ALTER TABLE `uin_users`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_login_key` (`user_login`),
  ADD KEY `user_nicename` (`user_nicename`),
  ADD KEY `user_email` (`user_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `syst_color`
--
ALTER TABLE `syst_color`
  MODIFY `color_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `syst_model`
--
ALTER TABLE `syst_model`
  MODIFY `model_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `syst_products`
--
ALTER TABLE `syst_products`
  MODIFY `product_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `syst_product_type`
--
ALTER TABLE `syst_product_type`
  MODIFY `type_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `uin_commentmeta`
--
ALTER TABLE `uin_commentmeta`
  MODIFY `meta_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `uin_comments`
--
ALTER TABLE `uin_comments`
  MODIFY `comment_ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `uin_links`
--
ALTER TABLE `uin_links`
  MODIFY `link_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `uin_options`
--
ALTER TABLE `uin_options`
  MODIFY `option_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=331;

--
-- AUTO_INCREMENT for table `uin_postmeta`
--
ALTER TABLE `uin_postmeta`
  MODIFY `meta_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `uin_posts`
--
ALTER TABLE `uin_posts`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `uin_termmeta`
--
ALTER TABLE `uin_termmeta`
  MODIFY `meta_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `uin_terms`
--
ALTER TABLE `uin_terms`
  MODIFY `term_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `uin_term_taxonomy`
--
ALTER TABLE `uin_term_taxonomy`
  MODIFY `term_taxonomy_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `uin_usermeta`
--
ALTER TABLE `uin_usermeta`
  MODIFY `umeta_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `uin_users`
--
ALTER TABLE `uin_users`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `syst_products`
--
ALTER TABLE `syst_products`
  ADD CONSTRAINT `syst_products_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_3` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_4` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_5` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_6` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`),
  ADD CONSTRAINT `syst_products_ibfk_7` FOREIGN KEY (`type_id`) REFERENCES `syst_product_type` (`type_id`);

--
-- Constraints for table `syst_product_has_variants`
--
ALTER TABLE `syst_product_has_variants`
  ADD CONSTRAINT `syst_product_has_variants_ibfk_1` FOREIGN KEY (`color_id`) REFERENCES `syst_color` (`color_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_10` FOREIGN KEY (`model_id`) REFERENCES `syst_model` (`model_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_11` FOREIGN KEY (`product_id`) REFERENCES `syst_products` (`product_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_2` FOREIGN KEY (`color_id`) REFERENCES `syst_color` (`color_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_3` FOREIGN KEY (`color_id`) REFERENCES `syst_color` (`color_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_4` FOREIGN KEY (`model_id`) REFERENCES `syst_model` (`model_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_5` FOREIGN KEY (`product_id`) REFERENCES `syst_products` (`product_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_6` FOREIGN KEY (`color_id`) REFERENCES `syst_color` (`color_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_7` FOREIGN KEY (`model_id`) REFERENCES `syst_model` (`model_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_8` FOREIGN KEY (`product_id`) REFERENCES `syst_products` (`product_id`),
  ADD CONSTRAINT `syst_product_has_variants_ibfk_9` FOREIGN KEY (`color_id`) REFERENCES `syst_color` (`color_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
